package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.MonthlyOrderCount;

import java.util.List;

public interface JpaOrderRepositoryCustom {

    Long getMonthlyMedian();

    public Long getMonthlyAverage();

    public List<MonthlyOrderCount> getMonthlyOrderCounts();
}
