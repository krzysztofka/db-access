package com.kali.dbAccess.generator;

import java.util.Collection;
import java.util.function.Consumer;

public interface BatchProcessor {

    <E> void process(Collection<E> items, Consumer<Collection<E>> consumer);
}
