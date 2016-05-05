package com.kali.dbAccess.generator;

import com.kali.dbAccess.domain.Customer;
import com.kali.dbAccess.repository.CustomerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component(value = "customersGenerator")
public class CustomersGenerator implements DataGenerator {

    @Autowired
    private CustomerRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void generateData(DataGenerationContext context, int quantity) {
        List<Customer> customers = IntStream.range(0, quantity)
                .mapToObj(x -> generateCustomer())
                .collect(Collectors.toList());
        List<Long> ids = repository.saveAll(customers);
        ids.forEach(context::addCustomer);
    }

    private Customer generateCustomer() {
        Customer customer = new Customer();
        customer.setAddress(randomAddress());
        customer.setEmail(randomEmail());
        return customer;
    }

    private String randomAddress() {
        return RandomStringUtils.randomAlphabetic(10).toString();
    }

    private String randomEmail() {
        return new StringBuilder().append(RandomStringUtils.randomAlphanumeric(6))
                .append(".")
                .append(RandomStringUtils.randomAlphanumeric(6))
                .append("@")
                .append(RandomStringUtils.randomAlphanumeric(5))
                .append(".")
                .append(RandomStringUtils.randomAlphanumeric(3))
                .toString();
    }
}
