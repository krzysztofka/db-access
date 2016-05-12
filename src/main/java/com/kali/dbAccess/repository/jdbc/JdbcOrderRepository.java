package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.domain.OrderItem;
import com.kali.dbaccess.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository("jdbcOrderRepository")
public class JdbcOrderRepository extends SimpleJdbcRepository<Order> implements OrderRepository {

    private static final String TABLE_NAME = "ORDERS";

    private static final String ORDER_ITEMS_TABLE_NAME = "ORDER_ITEMS";

    private static final Logger logger  = LoggerFactory.getLogger(JdbcOrderRepository.class);

    private SimpleJdbcInsert orderItemInsert;

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Order order) {
        return ImmutableMap.<String, Object>builder()
                .put("customer_id", order.getCustomer().getId())
                .put("order_date", order.getOrderDate())
                .build();
    }

    protected Map<String, Object> toParameters(OrderItem item) {
        return ImmutableMap.<String, Object>builder()
                .put("order_id", item.getOrder().getId())
                .put("product_id", item.getProduct().getId())
                .put("quantity", item.getQuantity())
                .build();
    }

    @Override
    protected Order saveEntityAndReturnKey(Order entity) {
        super.saveEntityAndReturnKey(entity);
        entity.getItems().stream().forEach(item -> orderItemInsert.execute(toParameters(item)));
        return entity;
    }

    @Autowired
    public void setOrderItemInsert(SimpleJdbcInsert orderItemInsert) {
        this.orderItemInsert = orderItemInsert.withTableName(ORDER_ITEMS_TABLE_NAME);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
