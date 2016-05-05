package com.kali.dbAccess.generator;

import java.util.*;

public class DataGenerationContext {

    private Set<Long> customerIds = new HashSet<>();

    private Set<Long> productIds = new HashSet<>();

    public Long getRandomProduct() {
        return getRandomValueFromArray(productIds);
    }

    public Long getRandomCustomer() {
        return getRandomValueFromArray(customerIds);
    }

    public void addProduct(Long id) {
        productIds.add(id);
    }

    public void addCustomer(Long id) {
        customerIds.add(id);
    }

    private Long getRandomValueFromArray(Set<Long> values) {
        List<Long> asList = new ArrayList<>(values);
        Collections.shuffle(asList);
        return asList.get(0);
    }
}
