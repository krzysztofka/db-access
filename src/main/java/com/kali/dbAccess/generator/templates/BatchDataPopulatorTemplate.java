package com.kali.dbaccess.generator.templates;

import com.kali.dbaccess.domain.Entity;
import com.kali.dbaccess.generator.InMemoryGenerationContext;
import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.providers.EntityProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BatchDataPopulatorTemplate<E extends Entity> implements DatabasePopulator {

    @Autowired
    private BatchProcessor batchProcessor;

    @Override
    public void populate(InMemoryGenerationContext context, int quantity) {
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

    protected abstract void persistAll(Collection<E> items, InMemoryGenerationContext context);

    protected abstract int batchSize();

    protected void generatePackage(InMemoryGenerationContext context, int quantity) {
        List<E> orders = IntStream.range(0, quantity)
                .mapToObj(x -> getEntityProvider().getEntity(context))
                .collect(Collectors.toList());

        batchProcessor.process(orders, (items) -> persistAll(items, context));
    }

    public void setBatchProcessor(BatchProcessor batchProcessor) {
        this.batchProcessor = batchProcessor;
    }
}
