package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.MonthlyOrderStats;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class JpaOrderRepositoryImpl implements JpaOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MonthlyOrderStats> getMonthlyOrderStats() {
        return entityManager.createNamedQuery("monthlyOrderStatsQuery", MonthlyOrderStats.class).getResultList();
    }
}
