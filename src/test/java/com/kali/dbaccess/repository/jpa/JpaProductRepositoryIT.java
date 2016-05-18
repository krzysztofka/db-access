package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.Product;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JpaProductRepositoryIT extends AbstractSpringIT {

    @Autowired
    private JpaProductRepository repository;

    @Test
    @Transactional
    public void itShouldReturnThreeBestsellerProducts() {
        List<Product> bestsellers = repository.getBestsellerProducts(new PageRequest(0, 3));
        Assert.assertTrue(bestsellers.size() == 3);
        bestsellers.forEach(this::assertProduct);

        bestsellers.forEach(x -> {
            x.setDescription("BESTSELLER... " + x.getDescription());
            int len = x.getDescription().length();

            if (len > 10000) {
                x.setDescription(x.getDescription().substring(0,  10000));
            }

            repository.save(x);

            Product result = repository.getOne(x.getId());
            Assert.assertTrue(result.getDescription().startsWith("BESTSELLER"));
            System.out.println("Bestseller: " + x.getId());
        });
    }

    private void assertProduct(Product product) {
        assertNotNull(product.getId());
        assertFalse(StringUtils.isEmpty(product.getDescription()));
        assertFalse(StringUtils.isEmpty(product.getName()));
        assertTrue(product.getPrice() > 0);
        assertNotNull(product.getSize());
    }
}
