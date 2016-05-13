package com.kali.dbaccess.generator.providers;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.generator.GenerationContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component("randomCustomerProvider")
public class RandomCustomerProvider implements EntityProvider<Customer> {

    @Override
    public Customer getEntity(GenerationContext context) {
        Customer customer = new Customer();
        customer.setName(randomName());
        customer.setAddress(randomAddress());
        customer.setEmail(randomEmail());
        return customer;
    }

    private String randomName() {
        return RandomStringUtils.randomAlphabetic(8) + " " + RandomStringUtils.randomAlphabetic(8);
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
                .append(RandomStringUtils.randomAlphabetic(3))
                .toString();
    }
}
