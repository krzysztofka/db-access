package com.kali.dbaccess.repository;

import com.kali.dbaccess.domain.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends Repository<Customer, Long> {

    List<Customer> getCustomersExceedingTargetPrice(int targetPrice, LocalDate from, LocalDate to);
}
