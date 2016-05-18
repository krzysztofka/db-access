package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.MonthlyOrderCount;

import java.util.List;

public interface JpaOrderRepositoryCustom {

    Long getMonthlyMedian();

    Long getMonthlyAverage();

    List<MonthlyOrderCount> getMonthlyOrderCounts();
}
