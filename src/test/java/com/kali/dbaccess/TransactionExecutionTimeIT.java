package com.kali.dbaccess;

import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.GenerationContext;
import com.kali.dbaccess.generator.InMemoryGenerationContext;
import com.kali.dbaccess.repository.ProductRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

@Ignore
public class TransactionExecutionTimeIT extends AbstractSpringIT {

    private static final int QUANTITY = 10000;

    private static final int REPETITIONS = 1000;

    @Autowired
    @Qualifier("productsPopulator")
    private DatabasePopulator populator;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private JdbcTemplate template;

    @Test
    public void testTransactionsExecutionTime() {
        long sum = 0;
        for (int i = 0; i < REPETITIONS; i++) {
            sum += getGenerationTimeForSingleTransaction();
        }

        System.out.println("### Duration: " + (sum / REPETITIONS));
    }

    private long getGenerationTimeForSingleTransaction() {
        long start = System.currentTimeMillis();

        GenerationContext context = new InMemoryGenerationContext();
        populator.populate(context, QUANTITY);

        long end = System.currentTimeMillis();
        System.out.println((end - start) + " MilliSeconds");
        return end - start;

    }

    private long getGenerationTimeForTransactionPerItem() {
        long start = System.currentTimeMillis();

        GenerationContext context = new InMemoryGenerationContext();
        for (int i = 0; i < QUANTITY; i++) {
            populator.populate(context, 1);
        }

        long end = System.currentTimeMillis();
        System.out.println((end - start) + " MilliSeconds");
        return end - start;
    }
}
