package com.kali.dbaccess.generator.templates;

import com.kali.dbaccess.domain.Entity;
import com.kali.dbaccess.generator.GenerationContext;
import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.logging.LogExecutionTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

public abstract class SimpleDataPopulatorTemplate<E extends Entity> implements DatabasePopulator {

    @Override
    @LogExecutionTime
    @Transactional
    public void populate(GenerationContext context, int quantity) {
        IntStream.range(0, quantity).forEach(x -> persist(getEntityProvider().getEntity(context)));
    }

    protected abstract void persist(E item);

    public abstract EntityProvider<E> getEntityProvider();
}
