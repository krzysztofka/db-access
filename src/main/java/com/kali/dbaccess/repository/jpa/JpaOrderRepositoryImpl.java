package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.MonthlyOrderCount;
import com.kali.dbaccess.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

public class JpaOrderRepositoryImpl implements JpaOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getMonthlyMedian() {
        return ((BigInteger) entityManager.createNativeQuery("select median(t.cnt) from (select count(o.id) as cnt " +
                "from ORDERS o group by YEAR(o.order_date), MONTH(o.order_date)) t").getSingleResult()).longValue();
    }

    @Override
    public Long getMonthlyAverage() {
        return ((BigInteger) entityManager.createNativeQuery("select avg(t.cnt) from (select count(o.id) as cnt " +
                "from ORDERS o group by YEAR(o.order_date), MONTH(o.order_date)) t").getSingleResult()).longValue();
    }

    @Override
    public List<MonthlyOrderCount> getMonthlyOrderCounts() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MonthlyOrderCount> q = cb.createQuery(MonthlyOrderCount.class);
        Root<Order> o = q.from(Order.class);

        Expression<Integer> year = cb.function("year", Integer.class, o.get("orderDate"));
        Expression<Integer> month = cb.function("month", Integer.class, o.get("orderDate"));

        q.groupBy(year, month).select(cb.construct(MonthlyOrderCount.class, month, year, cb.count(o.get("id"))));

        return entityManager.createQuery(q).getResultList();
    }
}
