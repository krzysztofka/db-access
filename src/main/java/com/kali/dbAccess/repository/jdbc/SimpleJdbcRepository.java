package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.domain.Entity;
import com.kali.dbaccess.repository.Repository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

public abstract class SimpleJdbcRepository<E extends Entity<Long>> {

    protected SimpleJdbcInsert simpleJdbcInsert;

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
        this.simpleJdbcInsert = simpleJdbcInsert
                .withTableName(tableName())
                .usingGeneratedKeyColumns(idColumn());
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Collection<E> saveAll(Collection<E> entities) {
        getLogger().debug("Inserting all " + entities.size() + " entities to " + tableName());

        entities.stream().forEach(this::save);

        getLogger().debug("Finished insertion of all " + entities.size() + " to " + tableName());
        return entities;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public E save(E entity) {
        return saveEntityAndReturnKey(entity);
    }

    protected String idColumn() {
        return "id";
    }

    protected abstract String tableName();

    protected E saveEntityAndReturnKey(E entity) {
        getLogger().debug("Inserting entity to " + tableName());

        Long id = simpleJdbcInsert.executeAndReturnKey(toParameters(entity)).longValue();
        entity.setId(id);
        getLogger().debug("Entity with id:  " + id + " inserted to " + tableName());
        return entity;
    }

    protected abstract Map<String, Object> toParameters(E entity);

    protected abstract Logger getLogger();
}
