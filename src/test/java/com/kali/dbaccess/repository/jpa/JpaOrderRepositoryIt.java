package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.MonthlyOrderStats;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JpaOrderRepositoryIT extends AbstractSpringIT {

    @Autowired
    private JpaOrderRepository repository;

    @Test
    public void itShouldReturnMonthlyOrderStats() {
        List<MonthlyOrderStats> monthlyOrderCounts = repository.getMonthlyOrderStats();
        Assert.assertFalse(monthlyOrderCounts.isEmpty());
        monthlyOrderCounts.forEach(x -> {
            assertTrue(x.getMonth() > 0 && x.getMonth() <= 12);
            assertTrue(x.getYear() > 2000 && x.getYear() < 3000);
            assertTrue(x.getAvg() >= 0);
            assertTrue(x.getMedian() >= 0);
            assertTrue(x.getSum() >= 0);
            System.out.println("Monthly orders: " + x.getMonth() + "-" + x.getYear() + " sum: " + x.getSum());
        });
    }
}
