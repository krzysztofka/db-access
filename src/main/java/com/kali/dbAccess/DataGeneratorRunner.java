package com.kali.dbAccess;

import com.kali.dbAccess.generator.DataGenerationContext;
import com.kali.dbAccess.generator.DatabasePopulator;
import com.kali.dbAccess.generator.OrdersPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGeneratorRunner implements CommandLineRunner {

    public static final int ORDERS_QUANTITY = 10000;

    public static final int CUSTOMER_QUANTITY = 1000;

    public static final int PRODUCTS_QUANTITY = 100;

    @Autowired
    @Qualifier(value = "customersPopulator")
    private DatabasePopulator customersPopulator;

    @Autowired
    @Qualifier(value = "productsPopulator")
    private DatabasePopulator productsPopulator;

    @Autowired
    @Qualifier(value = "ordersPopulator")
    private OrdersPopulator ordersPopulator;

    public void run(String... strings) throws Exception {
        DataGenerationContext context = new DataGenerationContext();

        customersPopulator.populate(context, CUSTOMER_QUANTITY);
        productsPopulator.populate(context, PRODUCTS_QUANTITY);
        ordersPopulator.populate(context, ORDERS_QUANTITY);
    }
}
