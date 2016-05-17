package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.MonthlyOrderCount;
import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.domain.OrderItem;
import com.kali.dbaccess.repository.CustomerRepository;
import com.kali.dbaccess.repository.OrderRepository;
import com.kali.dbaccess.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JdbcOrderRepositoryIT extends AbstractSpringIT {

    @Autowired
    @Qualifier("jdbcOrderRepository")
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Test
    public void itShouldInsertAndFindOrder() {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(customerRepository.getRandomEntities(1).stream().findAny().get());

        order.setItems(productRepository.getRandomEntities(2).stream().map(product -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(3);
            orderItem.setProductId(product.getId());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toSet()));

        repository.save(order);

        Order result = repository.getOne(order.getId());
        Assert.assertEquals(order, result);
    }

    @Test
    public void itShouldReturnRandomOrders() {
        int size = 4;
        Collection<Order> orders = repository.getRandomEntities(size);

        assertTrue(orders.size() == size);

        orders.stream().forEach(this::assertOrder);
        orders.stream().forEach(order -> assertNull(order.getItems()));
    }

    @Test
    public void itShouldFetchOrderItems() {
        Order order = repository.getRandomEntities(1).iterator().next();

        assertNotNull(order);

        Set<OrderItem> items = repository.fetchItems(order);

        items.stream().forEach(this::assertOrderItem);
    }

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

    private void assertOrder(Order order) {
        assertNotNull(order.getId());
        assertNotNull(order.getCustomer());
        assertNotNull(order.getOrderDate());
    }

    private void assertOrderItem(OrderItem orderItem) {
        assertNotNull(orderItem.getOrder());
        assertNotNull(orderItem.getProductId());
        assertTrue(orderItem.getQuantity() > 0);
    }

}
