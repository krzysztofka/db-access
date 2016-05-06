package com.kali.dbAccess.generator.providers;

import com.kali.dbAccess.domain.Customer;
import com.kali.dbAccess.generator.DataGenerationContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component("randomCustomerProvider")
public class RandomCustomerProvider implements EntityProvider<Customer> {

    @Override
    public Customer getEntity(DataGenerationContext context) {
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
                .append(RandomStringUtils.randomAlphabetic(3))
                .toString();
    }
}
