package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.domain.ProductSize;
import com.kali.dbaccess.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;

public class JdbcProductRepositoryIT extends AbstractSpringIT {

    @Autowired
    private ProductRepository repository;

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

        Product result = repository.find(product.getId());
        Assert.assertEquals(product, result);
    }

    @Test
    public void itShouldReturnRandomProducts() {
        int size = 4;
        Collection<Product> products = repository.getRandomEntities(size);

        assertTrue(products.size() == size);

        products.stream().forEach(this::assertProduct);
    }

    private void assertProduct(Product product) {
        assertNotNull(product.getId());
        assertFalse(StringUtils.isEmpty(product.getDescription()));
        assertFalse(StringUtils.isEmpty(product.getName()));
        assertTrue(product.getPrice() > 0);
        assertNotNull(product.getSize());
    }
}
