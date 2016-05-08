package com.kali.dbAccess.repository.jdbc;

import com.kali.dbAccess.domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SimpleJdbcRepository<E extends Entity> {


    protected SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert
                .withTableName(tableName())
                .usingGeneratedKeyColumns(idColumn());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Long> saveAll(Collection<E> entities) {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Long save(E entity) {
        return saveEntityAndReturnKey(entity);
    }

    protected String idColumn() {
        return "id";
    }

    protected abstract String tableName();


    protected Long saveEntityAndReturnKey(E entity) {
        return simpleJdbcInsert.executeAndReturnKey(toParameters(entity)).longValue();
    }

    protected abstract Map<String, Object> toParameters(E entity);
}
