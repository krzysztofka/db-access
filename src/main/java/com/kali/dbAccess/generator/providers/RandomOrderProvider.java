package com.kali.dbaccess.generator.providers;

import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.domain.OrderItem;
import com.kali.dbaccess.domain.Product;
import com.kali.dbaccess.generator.InMemoryGenerationContext;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("randomOrderProvider")
public class RandomOrderProvider implements EntityProvider<Order> {

    private static final long DATE_FROM = Timestamp.valueOf("2014-01-01 00:00:00").getTime();

    @Override
    public Order getEntity(InMemoryGenerationContext context) {
        Order order = new Order();
        order.setCustomer(context.getRandomCustomer());
        order.setOrderDate(randomDate());

        int orderItemsCount = RandomUtils.nextInt(1, 5);
        List<Product> products = context.getRandomUniqueProducts(orderItemsCount);
        Set<OrderItem> orderItems = products.stream()
                .map(product -> generateOrderItem(product, order))
                .collect(Collectors.toSet());
        order.setItems(orderItems);

        return order;
    }

    private OrderItem generateOrderItem(Product product, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setQuantity(RandomUtils.nextInt(1, 100));
        return orderItem;
    }

    private Date randomDate() {
        long diff = System.currentTimeMillis() - DATE_FROM;
        return new Date(DATE_FROM + (long) (Math.random() * diff));
    }
}
