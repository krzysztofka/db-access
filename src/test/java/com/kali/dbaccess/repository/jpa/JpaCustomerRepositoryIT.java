package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class JpaCustomerRepositoryIT extends AbstractSpringIT {

    @Autowired
    private JpaCustomerRepository repository;

    @Test
    public void itShouldReturnCustomersWhoReached10000() {
        Date from = Date.from(LocalDate.now().minusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        List<Customer> customers = repository
                .getCustomersExceedingTargetPrice(10000L, from, to);
        customers.forEach(this::assertCustomer);
        customers.forEach(x -> System.out.println("Customer who reached 10000$: " + x.getId()));

        System.out.println("Total:" + customers.size());
    }

    private void assertCustomer(Customer customer) {
        assertNotNull(customer.getId());
        assertFalse(StringUtils.isEmpty(customer.getName()));
        assertFalse(StringUtils.isEmpty(customer.getEmail()));
        assertFalse(StringUtils.isEmpty(customer.getAddress()));
    }

}


