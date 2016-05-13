package com.kali.dbaccess.generator;

import com.kali.dbaccess.repository.CustomerRepository;
import com.kali.dbaccess.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component("generationContextSupplier")
public class GenerationContextSupplier implements Supplier<GenerationContext> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public GenerationContext get() {
        return new DatabaseAwareGenerationContext(productRepository, customerRepository);
    }
}
