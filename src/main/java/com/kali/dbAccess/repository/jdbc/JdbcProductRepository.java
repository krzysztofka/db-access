package com.kali.dbAccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbAccess.domain.Product;
import com.kali.dbAccess.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcProductRepository extends SimpleJdbcRepository<Product> implements ProductRepository {

    private static final String TABLE_NAME = "PRODUCTS";

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    protected Map<String, Object> toParameters(Product product) {
        return ImmutableMap.<String, Object>builder()
                .put("name", product.getName())
                .put("description", product.getDescription())
                .put("price", product.getPrice())
                .build();
    }
}
