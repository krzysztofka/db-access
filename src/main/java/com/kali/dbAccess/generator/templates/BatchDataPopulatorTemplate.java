package com.kali.dbAccess.generator.templates;

import com.kali.dbAccess.domain.Entity;
import com.kali.dbAccess.generator.DataGenerationContext;
import com.kali.dbAccess.generator.DatabasePopulator;
import com.kali.dbAccess.generator.providers.EntityProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BatchDataPopulatorTemplate<E extends Entity> implements DatabasePopulator {

    @Autowired
    private BatchProcessor batchProcessor;

    public void populate(DataGenerationContext context, int quantity) {
        int batchSize = batchSize();
        while (quantity > batchSize) {
            quantity -= batchSize;
            generatePackage(context, batchSize);
        }
        if (quantity > 0) {
            generatePackage(context, quantity);
        }
    }

    public abstract EntityProvider<E> getEntityProvider();

    protected abstract void persistAll(Collection<E> items, DataGenerationContext context);

    protected abstract int batchSize();

    protected void generatePackage(DataGenerationContext context, int quantity) {
        List<E> orders = IntStream.range(0, quantity)
                .mapToObj(x -> getEntityProvider().getEntity(context))
                .collect(Collectors.toList());

        batchProcessor.process(orders, (items) -> persistAll(items, context));
    }

    public void setBatchProcessor(BatchProcessor batchProcessor) {
        this.batchProcessor = batchProcessor;
    }
}
