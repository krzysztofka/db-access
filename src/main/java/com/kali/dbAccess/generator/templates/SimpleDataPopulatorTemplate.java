package com.kali.dbaccess.generator.templates;

import com.kali.dbaccess.domain.Entity;
import com.kali.dbaccess.generator.InMemoryGenerationContext;
import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.providers.EntityProvider;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

public abstract class SimpleDataPopulatorTemplate<E extends Entity> implements DatabasePopulator {

    @Override
    @Transactional
    public void populate(InMemoryGenerationContext context, int quantity) {

        getLogger().debug("Populating database with " + quantity + " items.");
        IntStream.range(0, quantity).forEach(x -> persist(getEntityProvider().getEntity(context), context));
        getLogger().debug("Finished database population.");
    }

    public abstract void persist(E item, InMemoryGenerationContext context);

    public abstract EntityProvider<E> getEntityProvider();

    protected abstract Logger getLogger();
}
