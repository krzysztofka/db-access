package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.domain.Product;

import java.util.Collection;

public interface GenerationContext {

    Product getRandomProduct();

    Customer getRandomCustomer();

    Collection<Product> getRandomUniqueProducts(int quantity);
}
