package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.domain.Product;

import java.util.Collection;
import java.util.List;

public interface GenerationContext {

    Product getRandomProduct();

    Customer getRandomCustomer();

    List<Product> getRandomUniqueProducts(int quantity);

    void newProduct(Product product);

    void newProducts(Collection<Product> products);

    void newCustomer(Customer customer);

    void newCustomers(Collection<Customer> customers);
}
