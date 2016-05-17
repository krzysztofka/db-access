package com.kali.dbaccess.repository;

import com.kali.dbaccess.domain.MonthlyOrderCount;
import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.domain.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends Repository<Order, Long> {

    Set<OrderItem> fetchItems(Order order);

    Long getMonthlyMedian();

    Long getMonthlyAverage();

    List<MonthlyOrderCount> getMonthlyOrderCounts();
}
