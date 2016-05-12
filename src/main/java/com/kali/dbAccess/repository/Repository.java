package com.kali.dbaccess.repository;

import java.util.Collection;
public interface Repository<E> {

    E save(E entity);

    Collection<E> saveAll(Collection<E> entities);
}
