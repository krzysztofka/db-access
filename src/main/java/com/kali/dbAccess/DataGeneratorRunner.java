package com.kali.dbAccess;

import com.kali.dbAccess.generator.DataGenerationContext;
import com.kali.dbAccess.generator.DataGenerator;
import com.kali.dbAccess.generator.OrdersGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGeneratorRunner implements CommandLineRunner {

    private static final int ORDERS_QUANTITY = 10000;

    private static final int CUSTOMER_QUANTITY = 1000;

    private static final int PRODUCTS_QUANTITY = 100;

    @Autowired
    @Qualifier(value = "customersGenerator")
    private DataGenerator customersGenerator;

    @Autowired
    @Qualifier(value = "productsGenerator")
    private DataGenerator productsGenerator;

    @Autowired
    @Qualifier(value = "ordersGenerator")
    private OrdersGenerator ordersGenerator;

    public void run(String... strings) throws Exception {
        DataGenerationContext context = new DataGenerationContext();

        customersGenerator.generateData(context, CUSTOMER_QUANTITY);
        productsGenerator.generateData(context, PRODUCTS_QUANTITY);
        ordersGenerator.generateData(context, ORDERS_QUANTITY);
    }
}
