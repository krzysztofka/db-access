package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository(("jdbcProductRepository"))
public class JdbcProductRepository extends SimpleJdbcRepository<Product> implements ProductRepository {

    private static final String TABLE_NAME = "PRODUCTS";

    private static final Logger logger  = LoggerFactory.getLogger(JdbcProductRepository.class);

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Product product) {
        return ImmutableMap.<String, Object>builder()
                .put("name", product.getName())
                .put("description", product.getDescription())
                .put("price", product.getPrice())
                .put("size", product.getSize())
                .build();
    }

    @Override
    protected Logger getLogger() {
        return logger ;
    }
}
