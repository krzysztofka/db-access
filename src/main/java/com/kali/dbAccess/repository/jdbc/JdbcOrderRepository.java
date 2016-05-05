package com.kali.dbAccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbAccess.domain.Order;
import com.kali.dbAccess.repository.OrderRepository;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class JdbcOrderRepository extends SimpleJdbcRepository<Order> implements OrderRepository {

    private static final String TABLE_NAME = "ORDERS";

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    protected Map<String, Object> toParameters(Order order) {
        return ImmutableMap.<String, Object>builder()
                .put("customer_id", order.getCustomer().getId())
                .put("order_date", order.getOrderDate())
                .build();
    }

    protected Long saveEntity(Order entity) {
        Long orderId = super.saveEntity(entity);
        entity.getItems().stream().forEach(item ->
            saveEntity("order_items", ImmutableMap.<String, Object>builder()
                .put("order_id", orderId)
                .put("product_id", item.getProductId())
                .put("quantity", item.getQuantity())
                .build())
        );

        return orderId;
    }
}
