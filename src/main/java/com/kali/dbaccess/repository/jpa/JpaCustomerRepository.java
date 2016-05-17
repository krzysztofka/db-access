package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface JpaCustomerRepository extends CustomerRepository, JpaRepository<Customer, Long> {

    @Override
    @Query("select c from Order o join o.customer c join o.items i " +
            "join Product p on i.productId = p.id " +
            "where o.orderDate between ?#{[1]} and ?#{[2]} " +
            "group by c.id having sum(i.quantity * p.price) >= ?#{[0]}")
    List<Customer> getCustomersExceedingTargetPrice(int targetPrice, LocalDate from, LocalDate to);

    @Override
    @Query("select c from Customer c order by random()")
    Collection<Customer> getRandomEntities(int limit);
}
