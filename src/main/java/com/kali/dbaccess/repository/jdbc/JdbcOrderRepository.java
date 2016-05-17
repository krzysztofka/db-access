package com.kali.dbaccess.repository.jdbc;

import com.google.common.collect.ImmutableMap;
import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.domain.OrderItem;
import com.kali.dbaccess.repository.CustomerRepository;
import com.kali.dbaccess.repository.OrderRepository;
import com.kali.dbaccess.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("jdbcOrderRepository")
public class JdbcOrderRepository extends SimpleJdbcRepository<Order> implements OrderRepository {

    private static final String TABLE_NAME = "ORDERS";

    private static final String ORDER_ITEMS_TABLE_NAME = "ORDER_ITEMS";

    private static final String ID = "id";

    private static final String CUSTOMER_ID = "customer_id";

    private static final String ORDER_DATE = "order_date";

    private static final String ORDER_ID = "order_id";

    private static final String PRODUCT_ID = "product_id";

    private static final String QUANTITY = "quantity";

    private SimpleJdbcInsert orderItemInsert;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private RowMapper<Order> orderMapper = (rs, rowNum) -> {
        Order order = new Order();
        order.setId(rs.getLong(ID));
        order.setCustomer(customerRepository.find(rs.getLong(CUSTOMER_ID)));
        order.setOrderDate(new Date(rs.getTimestamp(ORDER_DATE).getTime()));
        return order;
    };

    private RowMapper<OrderItem> orderItemMapper = (rs, rowNum) -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(productRepository.find(rs.getLong(PRODUCT_ID)));
        orderItem.setQuantity(rs.getInt(QUANTITY));
        return orderItem;
    };

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> toParameters(Order order) {
        return ImmutableMap.<String, Object>builder()
                .put(CUSTOMER_ID, order.getCustomer().getId())
                .put(ORDER_DATE, order.getOrderDate())
                .build();
    }

    protected Map<String, Object> toParameters(OrderItem item) {
        return ImmutableMap.<String, Object>builder()
                .put(ORDER_ID, item.getOrder().getId())
                .put(PRODUCT_ID, item.getProduct().getId())
                .put(QUANTITY, item.getQuantity())
                .build();
    }

    @Override
    public Order find(Long id) {
        Order order = super.find(id);
        order.setItems(fetchItems(order));
        return order;
    }

    public Set<OrderItem> fetchItems(Order order) {
        return jdbcTemplate.query("SELECT * from ORDER_ITEMS i where i.order_id = ?",
                new Object[] {order.getId()}, orderItemMapper).stream().map(item -> {
            item.setOrder(order);
            return item;
        }).collect(Collectors.toSet());
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
    protected RowMapper<Order> rowMapper() {
        return orderMapper;
    }
}
