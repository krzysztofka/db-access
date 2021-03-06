package com.kali.dbaccess;

import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.GenerationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class DataGeneratorRunner implements CommandLineRunner {

    public static final int ORDERS_QUANTITY = 10000;

    public static final int CUSTOMER_QUANTITY = 1000;

    public static final int PRODUCTS_QUANTITY = 100;

    private static final Logger logger  = LoggerFactory.getLogger(DataGeneratorRunner.class);

    @Autowired
    @Qualifier(value = "customersPopulator")
    private DatabasePopulator customersPopulator;

    @Autowired
    @Qualifier(value = "productsPopulator")
    private DatabasePopulator productsPopulator;

    @Autowired
    @Qualifier(value = "ordersPopulator")
    private DatabasePopulator ordersPopulator;

    @Autowired
    @Qualifier("generationContextSupplier")
    private Supplier<GenerationContext> generationContextSupplier;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Data generation started");

        GenerationContext context = generationContextSupplier.get();

        customersPopulator.populate(context, CUSTOMER_QUANTITY);
        productsPopulator.populate(context, PRODUCTS_QUANTITY);
        ordersPopulator.populate(context, ORDERS_QUANTITY);

        logger.info("Data generation finished");
    }

    public void setCustomersPopulator(DatabasePopulator customersPopulator) {
        this.customersPopulator = customersPopulator;
    }

    public void setProductsPopulator(DatabasePopulator productsPopulator) {
        this.productsPopulator = productsPopulator;
    }

    public void setOrdersPopulator(DatabasePopulator ordersPopulator) {
        this.ordersPopulator = ordersPopulator;
    }

    public void setGenerationContextSupplier(Supplier<GenerationContext> generationContextSupplier) {
        this.generationContextSupplier = generationContextSupplier;
    }
}
