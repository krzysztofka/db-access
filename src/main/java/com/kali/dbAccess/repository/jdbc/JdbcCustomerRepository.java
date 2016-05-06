package com.kali.dbAccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbAccess.domain.Customer;
import com.kali.dbAccess.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcCustomerRepository extends SimpleJdbcRepository<Customer> implements CustomerRepository {

    private static final String TABLE_NAME = "CUSTOMERS";

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Customer customer) {
        return ImmutableMap.<String, Object>builder()
                .put("address", customer.getAddress())
                .put("email", customer.getEmail())
                .build();
    }
}
