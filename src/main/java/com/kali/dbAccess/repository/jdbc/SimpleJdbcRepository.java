package com.kali.dbAccess.repository.jdbc;

import com.kali.dbAccess.domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SimpleJdbcRepository<E extends Entity> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Long> saveAll(Collection<E> entities) {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Long save(E entity) {
        return saveEntity(entity);
    }

    protected String idColumn() {
        return "id";
    }

    protected abstract String tableName();

    protected Long saveEntity(E entity) {
        return saveEntity(tableName(), idColumn(), toParameters(entity));
    }

    protected void saveEntity(String table, Map<String, Object> parameters) {
        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(table)
                .execute(new MapSqlParameterSource(parameters));
    }

    protected Long saveEntity(String table, String idColumn, Map<String, Object> parameters) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(table)
                .usingGeneratedKeyColumns(idColumn)
                .executeAndReturnKey(new MapSqlParameterSource(parameters))
                .longValue();
    }

    protected abstract Map<String, Object> toParameters(E entity);
}
