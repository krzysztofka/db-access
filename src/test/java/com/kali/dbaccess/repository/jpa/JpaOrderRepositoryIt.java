package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.MonthlyOrderCount;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JpaOrderRepositoryIT extends AbstractSpringIT {

    @Autowired
    private JpaOrderRepository repository;

    @Test
    public void itShouldReturnAvgMonthlyOrders() {
        long avg = repository.getMonthlyAverage();
        Assert.assertTrue(avg > 0);
        System.out.println("average: " + avg);
    }

    @Test
    public void itShouldReturnMedianMonthlyOrders() {
        long median = repository.getMonthlyMedian();
        Assert.assertTrue(median > 0);
        System.out.println("median: " + median);
    }

    @Test
    public void itShouldReturnMonthlyOrderCounts() {
        List<MonthlyOrderCount> monthlyOrderCounts = repository.getMonthlyOrderCounts();
        Assert.assertFalse(monthlyOrderCounts.isEmpty());
        monthlyOrderCounts.forEach(x -> {
            assertTrue(x.getMonth() > 0 && x.getMonth() <= 12);
            assertTrue(x.getYear() > 2000 && x.getYear() < 3000);
            assertTrue(x.getCount() >= 0);
            System.out.println("Monthly orders: " + x.getMonth() + "-" + x.getYear() + " count: " + x.getCount());
        });
    }
}
