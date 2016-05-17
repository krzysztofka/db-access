package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends CustomerRepository, JpaRepository<Customer, Long> {
}
