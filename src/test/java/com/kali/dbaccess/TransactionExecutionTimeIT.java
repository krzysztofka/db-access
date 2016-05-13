package com.kali.dbaccess;

import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.InMemoryGenerationContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TransactionExecutionTimeIT extends AbstractSpringIT {

    private static final int QUANTITY = 10000;

    @Autowired
    @Qualifier("productsPopulator")
    private DatabasePopulator populator;

    @Test
    public void testOneTransactionPerDataSet() {
        long start = System.currentTimeMillis();
        InMemoryGenerationContext context = new InMemoryGenerationContext();
        populator.populate(context, QUANTITY);

        long end = System.currentTimeMillis();
        System.out.println("testOneTransactionPerDataSet: " + (end - start) + " MilliSeconds");
    }

    @Test
    public void testTransactionsPerEachProduct() {
        long start = System.currentTimeMillis();

        InMemoryGenerationContext context = new InMemoryGenerationContext();
        for (int i = 0; i < QUANTITY; i++) {
            populator.populate(context, 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("testOneTransactionPerItem: " + (end - start) + " MilliSeconds");
    }
}
