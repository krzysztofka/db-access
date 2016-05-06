package com.kali.dbAccess.generator.templates;

import com.kali.dbAccess.domain.Entity;
import com.kali.dbAccess.generator.DataGenerationContext;
import com.kali.dbAccess.generator.DatabasePopulator;
import com.kali.dbAccess.generator.providers.EntityProvider;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

public abstract class SimpleDataPopulatorTemplate<E extends Entity> implements DatabasePopulator {

    @Override
    @Transactional
    public void populate(DataGenerationContext context, int quantity) {
        IntStream.range(0, quantity).forEach(x -> persist(getEntityProvider().getEntity(context), context));
    }

    public abstract void persist(E item, DataGenerationContext context);

    public abstract EntityProvider<E> getEntityProvider();
}
