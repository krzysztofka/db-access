package com.kali.dbaccess.generator.templates;

import com.kali.dbaccess.domain.BaseEntity;
import com.kali.dbaccess.generator.GenerationContext;
import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.logging.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BatchDataPopulatorTemplate<E extends BaseEntity> implements DatabasePopulator {

    @Autowired
    private BatchProcessor batchProcessor;

    @Override
    @LogExecutionTime
    public void populate(GenerationContext context, int quantity) {
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

    protected abstract void persistAll(Collection<E> items, GenerationContext context);

    protected abstract int batchSize();

    protected void generatePackage(GenerationContext context, int quantity) {
        List<E> orders = IntStream.range(0, quantity)
                .mapToObj(x -> getEntityProvider().getEntity(context))
                .collect(Collectors.toList());

        batchProcessor.process(orders, (items) -> persistAll(items, context));
    }

    public void setBatchProcessor(BatchProcessor batchProcessor) {
        this.batchProcessor = batchProcessor;
    }
}
