package com.kali.dbaccess.repository.jdbc;

import com.kali.dbaccess.AbstractSpringIT;
import com.kali.dbaccess.domain.MonthlyOrderStats;
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
            orderItem.setProduct(product);
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
    public void itShouldReturnMonthlyOrderCounts() {
        List<MonthlyOrderStats> monthlyOrderCounts = repository.getMonthlyOrderStats();
        monthlyOrderCounts.forEach(x -> {
            assertTrue(x.getMonth() > 0 && x.getMonth() <= 12);
            assertTrue(x.getYear() > 2000 && x.getYear() < 3000);
            assertTrue(x.getSum() > 0);
            assertTrue(x.getMedian() > 0);
            assertTrue(x.getAvg() > 0);
            System.out.println("Monthly orders: " + x.getMonth() + "-" + x.getYear() + " sum: " + x.getSum());
        });
    }

    private void assertOrder(Order order) {
        assertNotNull(order.getId());
        assertNotNull(order.getCustomer());
        assertNotNull(order.getOrderDate());
    }

    private void assertOrderItem(OrderItem orderItem) {
        assertNotNull(orderItem.getOrder());
        assertNotNull(orderItem.getProduct());
        assertTrue(orderItem.getQuantity() > 0);
    }

}
