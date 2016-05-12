package com.kali.dbaccess.generator.providers;

import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.domain.ProductSize;
import com.kali.dbaccess.generator.InMemoryGenerationContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("randomProductProvider")
public class RandomProductProvider implements EntityProvider<Product> {

    private Random random = new Random();

    @Override
    public Product getEntity(InMemoryGenerationContext context) {
        Product product = new Product();
        product.setName(randomName());
        product.setDescription(randomDescription());
        product.setPrice(randomPrice());
        product.setSize(randomSize());
        return product;
    }

    private String randomName() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    private String randomDescription() {
        return RandomStringUtils.randomAlphabetic(40);
    }

    private Integer randomPrice() {
        return RandomUtils.nextInt(10, 9000);
    }

    private ProductSize randomSize() {
        return ProductSize.values()[random.nextInt(ProductSize.values().length)];
    }
}
