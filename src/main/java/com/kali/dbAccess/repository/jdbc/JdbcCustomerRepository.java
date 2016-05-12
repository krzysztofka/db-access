package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcCustomerRepository extends SimpleJdbcRepository<Customer> implements CustomerRepository {

    private static final String TABLE_NAME = "CUSTOMERS";

    private static final Logger logger  = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Customer customer) {
        return ImmutableMap.<String, Object>builder()
                .put("name", customer.getName())
                .put("address", customer.getAddress())
                .put("email", customer.getEmail())
                .build();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
