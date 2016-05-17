package com.kali.dbaccess.repository;

import java.util.Collection;
import java.util.List;

public interface Repository<E, ID> {

    <S extends E> S save(S entity);

    <S extends E> List<S> save(Iterable<S> var1);

    E getOne(ID id);

    Collection<E> getRandomEntities(int limit);
}
