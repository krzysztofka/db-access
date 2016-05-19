package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.MonthlyOrderStats;

import java.util.List;

public interface JpaOrderRepositoryCustom {

    List<MonthlyOrderStats> getMonthlyOrderStats();
}
