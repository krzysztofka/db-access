package com.kali.dbAccess.generator.providers;

import com.kali.dbAccess.domain.Customer;
import com.kali.dbAccess.domain.Order;
import com.kali.dbAccess.domain.OrderItem;
import com.kali.dbAccess.generator.DataGenerationContext;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component("randomOrderProvider")
public class RandomOrderProvider implements EntityProvider<Order> {

    @Override
    public Order getEntity(DataGenerationContext context) {
        Order order = new Order();
        order.setCustomer(new Customer(context.getRandomCustomer()));
        order.setOrderDate(new Date());

        Set<OrderItem> orderItems = IntStream.range(0, RandomUtils.nextInt(1, 5))
                .mapToObj(x -> generateOrderItem(context))
                .collect(Collectors.toSet());
        order.setItems(orderItems);

        return order;
    }

    private OrderItem generateOrderItem(DataGenerationContext context) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(context.getRandomProduct());
        orderItem.setQuantity(RandomUtils.nextInt(1, 100));
        return orderItem;
    }
}
