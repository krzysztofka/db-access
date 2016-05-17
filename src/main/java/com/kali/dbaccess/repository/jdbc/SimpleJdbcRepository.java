package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.Lists;
import com.kali.dbaccess.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class SimpleJdbcRepository<E extends BaseEntity<Long>> {

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
    public <S extends E> List<S> save(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::save).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public <S extends E> S save(S entity) {
        return saveEntityAndReturnKey(entity);
    }

    public E getOne(Long id) {
        String query = new StringBuilder("SELECT * FROM ")
                .append(tableName())
                .append(" WHERE ")
                .append(idColumn())
                .append(" = ?")
                .toString();
        return jdbcTemplate.queryForObject(query, new Object[]{id}, rowMapper());
    }

    public void update(E entity, String[] properties, List<Object> values) {
        StringBuilder updateBuilder = new StringBuilder("UPDATE ")
        .append(tableName())
        .append(" SET ")
        .append(toUpdateColumnsStatement(properties))
        .append(" WHERE ")
        .append(idColumn())
        .append(" = ?");

        List<Object> args = Lists.newArrayList(values);
        args.add(entity.getId());

        jdbcTemplate.update(updateBuilder.toString(), args.toArray());
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

    protected <S extends E> S saveEntityAndReturnKey(S entity) {
        Long id = simpleJdbcInsert.executeAndReturnKey(toParameters(entity)).longValue();
        entity.setId(id);
        return entity;
    }

    protected String toColumnStrings(String alias, Set<String> columns) {
        return columns.stream().map(c -> alias + "." + c).collect(Collectors.joining(", "));
    }

    protected java.sql.Date toSqlDate(LocalDate localDate) {
        return new java.sql.Date(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    protected abstract Map<String, Object> toParameters(E entity);

    private String toUpdateColumnsStatement(String[] props) {
        return Stream.of(props).map(prop -> prop + " = ?").collect(Collectors.joining(", "));
    }
}
