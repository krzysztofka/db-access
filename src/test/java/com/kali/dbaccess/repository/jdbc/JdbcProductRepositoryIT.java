package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.domain.ProductSize;
import com.kali.dbaccess.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

public class JdbcProductRepositoryIT extends AbstractSpringIT {

    @Autowired
    @Qualifier("jdbcProductRepository")
    private ProductRepository repository;

    @Autowired
    private JdbcTemplate template;

    @Transactional
    @Test
    public void itShouldInsertProductWith10KDescriptionSize() {
        Product product = new Product();
        product.setName("10K product");

        String description = RandomStringUtils.randomAlphabetic(10000);
        product.setDescription(description);
        product.setSize(ProductSize.MEDIUM);
        product.setPrice(10000);

        repository.save(product);

        int count = template.queryForObject("select count(*) from PRODUCTS where id = ?",
                        new Object[]{product.getId()}, Integer.class);
        assertEquals(count, 1);

        template.query("select description from PRODUCTS where id = ?", pss -> pss.setLong(1, product.getId()), rch -> {
            assertEquals(description, rch.getString("description"));
        });
    }
}
