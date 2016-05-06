package com.kali.dbAccess.generator.providers;

import com.kali.dbAccess.domain.Product;
import com.kali.dbAccess.generator.DataGenerationContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

@Component("randomProductProvider")
public class RandomProductProvider implements EntityProvider<Product> {

    @Override
    public Product getEntity(DataGenerationContext context) {
        Product product = new Product();
        product.setName(randomName());
        product.setDescription(randomDescription());
        product.setPrice(randomPrice());
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
}
