package com.kali.dbaccess.generator;

import com.google.common.base.Preconditions;
import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.domain.Product;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class InMemoryGenerationContext implements GenerationContext{

    private List<Customer> customers = new ArrayList<>();

    private List<Product> products = new ArrayList<>();

    @Override
    public Product getRandomProduct() {
        return getRandomValue(products);
    }

    @Override
    public Customer getRandomCustomer() {
        return getRandomValue(customers);
    }

    public List<Product> getRandomUniqueProducts(int quantity) {
        return getUniqueRandomValues(products, quantity);
    }

    public void newProduct(Product product) {
        products.add(product);
    }

    public void newProducts(Collection<Product> products) {
        this.products.addAll(products);
    }

    public void newCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void newCustomers(Collection<Customer> customers) {
        this.customers.addAll(customers);
    }

    private <E> E getRandomValue(List<E> values) {
        return getUniqueRandomValues(values, 1).get(0);
    }

    private <E> List<E> getUniqueRandomValues(List<E> values, int quantity) {
        Preconditions.checkArgument(quantity > 0, "quantity must be positive");
        Preconditions.checkArgument(values.size() >= quantity, "quantity cannot exceed values size");

        return ThreadLocalRandom.current()
                .ints(0, values.size())
                .distinct()
                .limit(quantity)
                .mapToObj(values::get)
                .collect(Collectors.toList());
    }
}
