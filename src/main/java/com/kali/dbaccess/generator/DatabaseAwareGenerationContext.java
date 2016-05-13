package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.repository.CustomerRepository;
import com.kali.dbaccess.repository.ProductRepository;

import java.util.Collection;

public class DatabaseAwareGenerationContext implements GenerationContext {

    private ProductRepository productRepository;

    private CustomerRepository customerRepository;

    public DatabaseAwareGenerationContext(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Product getRandomProduct() {
        return productRepository.getRandomEntities(1).stream().findAny().get();
    }

    @Override
    public Customer getRandomCustomer() {
        return customerRepository.getRandomEntities(1).stream().findAny().get();
    }

    @Override
    public Collection<Product> getRandomUniqueProducts(int quantity) {
        return productRepository.getRandomEntities(quantity);
    }
}
