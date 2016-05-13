package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        entities.stream().forEach(this::save);
        return entities;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public E save(E entity) {
        return saveEntityAndReturnKey(entity);
    }

    public E find(Long id) {
        String query = new StringBuilder("SELECT * FROM ")
                .append(tableName())
                .append(" WHERE ")
                .append(idColumn())
                .append(" = ?")
                .toString();
        return jdbcTemplate.queryForObject(query, new Object[]{id}, rowMapper());
    }

    @Transactional
    public Collection<E> getRandomEntities(int limit) {
        return jdbcTemplate.query("SELECT * FROM " + tableName() + " ORDER BY RAND() LIMIT ?",
                new Object[] {limit}, rowMapper());
    }

    protected abstract RowMapper<E> rowMapper();

    protected String idColumn() {
        return "id";
    }

    protected abstract String tableName();

    protected E saveEntityAndReturnKey(E entity) {
        Long id = simpleJdbcInsert.executeAndReturnKey(toParameters(entity)).longValue();
        entity.setId(id);
        return entity;
    }

    protected abstract Map<String, Object> toParameters(E entity);
}
