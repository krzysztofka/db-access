package com.kali.dbAccess.repository;

import java.util.Collection;
import java.util.List;

public interface Repository<E> {

    Long save(E entity);

    List<Long> saveAll(Collection<E> entities);
}
