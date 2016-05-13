package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JdbcCustomerRepositoryIT extends AbstractSpringIT {

    @Autowired
    private CustomerRepository repository;

    @Transactional
    @Test
    public void itShouldInsertAndFindCustomer() {
        Customer customer = new Customer();
        customer.setName("Test customer");
        customer.setAddress("address");
        customer.setEmail("test@email.com");

        repository.save(customer);

        Customer result = repository.find(customer.getId());
        Assert.assertEquals(customer, result);
    }

    @Test
    public void itShouldReturnRandomCustomers() {
        int size = 3;
        Collection<Customer> customers = repository.getRandomEntities(size);

        assertTrue(customers.size() == size);

        customers.stream().forEach(this::assertCustomer);
    }

    private void assertCustomer(Customer customer) {
        assertNotNull(customer.getId());
        assertFalse(StringUtils.isEmpty(customer.getName()));
        assertFalse(StringUtils.isEmpty(customer.getEmail()));
        assertFalse(StringUtils.isEmpty(customer.getAddress()));
    }
}
