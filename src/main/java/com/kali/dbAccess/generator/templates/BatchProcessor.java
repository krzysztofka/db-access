package com.kali.dbaccess.generator.templates;

import java.util.Collection;
import java.util.function.Consumer;

public interface BatchProcessor {

    <E> void process(Collection<E> items, Consumer<Collection<E>> consumer);
}
