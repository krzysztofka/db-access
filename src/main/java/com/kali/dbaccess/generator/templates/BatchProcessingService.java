package com.kali.dbaccess.generator.templates;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.function.Consumer;

@Service
public class BatchProcessingService implements BatchProcessor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <E> void process(Collection<E> items, Consumer<Collection<E>> consumer) {
        consumer.accept(items);
    }
}
