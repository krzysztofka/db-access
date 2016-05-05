package com.kali.dbAccess.generator;

import com.kali.dbAccess.domain.Product;
import com.kali.dbAccess.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component(value = "productsGenerator")
public class ProductsGenerator implements DataGenerator {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public void generateData(DataGenerationContext context, int quantity) {
        List<Product> products = IntStream.range(0, quantity)
                .mapToObj(x -> generateProduct())
                .collect(Collectors.toList());
        List<Long> ids = repository.saveAll(products);
        ids.forEach(context::addProduct);
    }

    public Product generateProduct() {
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
