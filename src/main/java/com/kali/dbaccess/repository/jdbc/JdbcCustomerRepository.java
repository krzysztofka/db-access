package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.repository.CustomerRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository("jdbcCustomerRepository")
public class JdbcCustomerRepository extends SimpleJdbcRepository<Customer> implements CustomerRepository {

    private static final String TABLE_NAME = "CUSTOMERS";

    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String ADDRESS = "address";

    private static final String EMAIL = "email";

    private static final Set<String> ALL_COLUMNS = ImmutableSet.of(ID, NAME, ADDRESS, EMAIL);

    private RowMapper<Customer> mapper = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getLong(ID));
        customer.setName(rs.getString(NAME));
        customer.setEmail(rs.getString(EMAIL));
        customer.setAddress(rs.getString(ADDRESS));
        return customer;
    };

    @Override
    public List<Customer> getCustomersExceedingTargetPrice(int targetPrice, final LocalDate from, final LocalDate to) {
        String sqlColumns = toColumnStrings("c", ALL_COLUMNS);
        StringBuilder queryBuilder = new StringBuilder("select ").append(sqlColumns)
                .append(" from Customers c join Orders o on o.customer_id = c.id")
                .append(" join order_items oi on o.id = oi.order_id")
                .append(" join products p on oi.product_id = p.id")
                .append(" where o.order_date between ? and ?")
                .append(" group by c.id having sum (oi.quantity * p.price) >= ?");

        return jdbcTemplate.query(queryBuilder.toString(),  (PreparedStatement ps) -> {
            ps.setDate(1, toSqlDate(from));
            ps.setDate(2, toSqlDate(to));
            ps.setInt(3, targetPrice);
        }, rowMapper());
    }

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Customer customer) {
        return ImmutableMap.<String, Object>builder()
                .put(NAME, customer.getName())
                .put(ADDRESS, customer.getAddress())
                .put(EMAIL, customer.getEmail())
                .build();
    }

    @Override
    protected RowMapper<Customer> rowMapper() {
        return mapper;
    }
}
