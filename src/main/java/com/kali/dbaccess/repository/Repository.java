package com.kali.dbaccess.repository;

import java.util.Collection;
public interface Repository<E, ID> {

    E save(E entity);

    Collection<E> saveAll(Collection<E> entities);

    E find(ID id);

    Collection<E> getRandomEntities(int limit);
}
