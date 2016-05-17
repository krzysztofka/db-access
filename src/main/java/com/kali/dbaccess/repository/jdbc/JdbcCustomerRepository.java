package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.repository.CustomerRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcCustomerRepository extends SimpleJdbcRepository<Customer> implements CustomerRepository {

    private static final String TABLE_NAME = "CUSTOMERS";

    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String ADDRESS = "address";

    private static final String EMAIL = "email";

    private RowMapper<Customer> mapper = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getLong(ID));
        customer.setName(rs.getString(NAME));
        customer.setEmail(rs.getString(EMAIL));
        customer.setAddress(rs.getString(ADDRESS));
        return customer;
    };

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
