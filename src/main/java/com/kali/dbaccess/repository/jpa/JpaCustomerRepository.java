package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from com.kali.dbaccess.domain.Order o join o.customer c join o.items i, " +
            "com.kali.dbaccess.domain.Product p " +
            "where i.productId = p.id and o.orderDate between ?2 and ?3 " +
            "group by c.id having sum(i.quantity * p.price) >= ?1")
    List<Customer> getCustomersExceedingTargetPrice(
            long targetPrice, Date from, Date to);
}
