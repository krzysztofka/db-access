package com.kali.dbaccess.generator.templates;

import com.kali.dbaccess.domain.BaseEntity;
import com.kali.dbaccess.generator.GenerationContext;
import com.kali.dbaccess.generator.DatabasePopulator;
import com.kali.dbaccess.generator.providers.EntityProvider;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

public abstract class SimpleDataPopulatorTemplate<E extends BaseEntity> implements DatabasePopulator {

    @Override
    //@LogExecutionTime
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void populate(GenerationContext context, int quantity) {
        IntStream.range(0, quantity).forEach(x -> persist(getEntityProvider().getEntity(context)));
    }

    protected abstract void persist(E item);

    public abstract EntityProvider<E> getEntityProvider();
}
