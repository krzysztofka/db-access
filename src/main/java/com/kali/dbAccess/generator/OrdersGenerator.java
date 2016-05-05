package com.kali.dbAccess.generator;

import com.kali.dbAccess.domain.Customer;
import com.kali.dbAccess.domain.Order;
import com.kali.dbAccess.domain.OrderItem;
import com.kali.dbAccess.repository.OrderRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component(value = "ordersGenerator")
public class OrdersGenerator implements DataGenerator {

    private static final int BATCH_SIZE = 100;

    @Autowired
    private BatchProcessor batchProcessor;

    @Autowired
    private OrderRepository repository;

    public void generateData(DataGenerationContext context, int quantity) {
        while( quantity > BATCH_SIZE) {
            quantity -= BATCH_SIZE;
            generatePackage(context, BATCH_SIZE);
        }
        if (quantity > 0) {
            generatePackage(context, quantity);
        }
    }

    private void generatePackage(DataGenerationContext context, int quantity) {
        List<Order> orders = IntStream.range(0, quantity)
                .mapToObj(x -> generateOrder(context))
                .collect(Collectors.toList());

        batchProcessor.process(orders, repository::saveAll);
    }

    private Order generateOrder(DataGenerationContext context) {
        Order order = new Order();
        order.setCustomer(new Customer(context.getRandomCustomer()));

        Set<OrderItem> orderItems = IntStream.range(0, RandomUtils.nextInt(1, 5))
                .mapToObj(x -> generateOrderItem(context))
                .collect(Collectors.toSet());
        order.setItems(orderItems);
        order.setOrderDate(new Date());

        return order;
    }

    private OrderItem generateOrderItem(DataGenerationContext context) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(context.getRandomProduct());
        orderItem.setQuantity(RandomUtils.nextInt(1, 100));
        return orderItem;
    }


}
