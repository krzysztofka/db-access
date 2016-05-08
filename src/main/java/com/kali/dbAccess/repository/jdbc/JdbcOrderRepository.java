package com.kali.dbAccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbAccess.domain.Order;
import com.kali.dbAccess.domain.OrderItem;
import com.kali.dbAccess.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class JdbcOrderRepository extends SimpleJdbcRepository<Order> implements OrderRepository {

    private static final String TABLE_NAME = "ORDERS";

    private static final String ORDER_ITEMS_TABLE_NAME = "ORDER_ITEMS";

    private SimpleJdbcInsert orderItemInsert;

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

    protected Map<String, Object> toParameters(OrderItem item) {
        return ImmutableMap.<String, Object>builder()
                .put("order_id", item.getOrderId())
                .put("product_id", item.getProductId())
                .put("quantity", item.getQuantity())
                .build();
    }

    @Override
    protected Long saveEntityAndReturnKey(Order entity) {
        Long orderId = super.saveEntityAndReturnKey(entity);
        entity.getItems().stream().forEach(item -> {
            item.setOrderId(orderId);
            orderItemInsert.execute(toParameters(item));
        });
        return orderId;
    }

    @Autowired
    public void setOrderItemInsert(SimpleJdbcInsert orderItemInsert) {
        this.orderItemInsert = orderItemInsert.withTableName(ORDER_ITEMS_TABLE_NAME);
    }
}
