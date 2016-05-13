package com.kali.dbaccess;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.*;

public class DataPopulationIT extends AbstractSpringIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testOrderToCustomerRelation() {
        int result = jdbcTemplate.queryForObject(
                "select count (*) from orders where customer_id is null", Integer.class);
        assertEquals("Order without customer id found", 0, result);
    }

    @Test
    public void testOrderToOrderItemsRelation() {
        List<Integer> result = jdbcTemplate.queryForList(
                "select o.id from orders o left join order_items oi on o.id = oi.order_id group by o.id " +
                        "having count(oi.order_id) = 0", Integer.class);
        assertTrue("Order without order items found", result.isEmpty());
    }

    @Test
    public void testOrderItemToProductRelation() {
        int result = jdbcTemplate.queryForObject(
                "select count (*) from order_items where product_id is null", Integer.class);
        assertEquals("Order item without product found", 0, result);
    }

    private void assertCount(String table, int count) {
        int result = jdbcTemplate.queryForObject("SELECT count(*) FROM " + table, Integer.class);
        assertEquals("Wrong size of table: " + table, count, result);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
