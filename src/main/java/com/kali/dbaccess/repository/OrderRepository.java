package com.kali.dbaccess.repository;

import com.kali.dbaccess.domain.MonthlyOrderStats;
import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.domain.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends Repository<Order, Long> {

    Set<OrderItem> fetchItems(Order order);

    List<MonthlyOrderStats> getMonthlyOrderStats();
}
