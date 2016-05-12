package com.kali.dbaccess.repository.jdbc

import com.kali.dbaccess.domain.Customer
import com.kali.dbaccess.domain.Order
import com.kali.dbaccess.domain.OrderItem
import com.kali.dbaccess.domain.Product
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import spock.lang.Specification

class JdbcOrderRepositorySpec extends Specification {

    def repository = new JdbcOrderRepository();

    def order = new Order([customer: new Customer([id: 3L, name: 'Cust']), orderDate: new Date()])

    def orderItems =  [
            new OrderItem([order: order, product: new Product([id: 4L]), quantity: 4]),
            new OrderItem([order: order, product: new Product([id: 5]), quantity: 1])]

    def simpleJdbcInsert = Mock(SimpleJdbcInsert)

    def orderItemsInsert = Mock(SimpleJdbcInsert)

    def setup() {
        order.setItems(orderItems as Set)

        simpleJdbcInsert.withTableName("ORDERS") >> simpleJdbcInsert
        simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
        repository.setSimpleJdbcInsert(simpleJdbcInsert)

        orderItemsInsert.withTableName("ORDER_ITEMS") >> orderItemsInsert
        repository.setOrderItemInsert(orderItemsInsert)
    }

    def "should init jdbc insert"() {
        when:
        repository.setSimpleJdbcInsert(simpleJdbcInsert)

        then:
        1 * simpleJdbcInsert.withTableName("ORDERS") >> simpleJdbcInsert
        1 * simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
    }

    def "should init order items jdbc insert"() {
        when:
        repository.setOrderItemInsert(orderItemsInsert)

        then:
        1 * orderItemsInsert.withTableName("ORDER_ITEMS")
    }

    def "should map order to table columns"() {
        when:
        def paramsMap = repository.toParameters(order)

        then:
        assert order.getCustomer().getId().equals(paramsMap.get('customer_id'))
        assert order.getOrderDate().equals(paramsMap.get('order_date'))
        assert !paramsMap.containsKey('id')
    }

    def "should map order item to table columns"() {
        given:
        def orderItem = orderItems[0];
        orderItem.setOrder(new Order([id: 33L]))

        when:
        def paramsMap = repository.toParameters(orderItem)

        then:
        assert orderItem.getOrder().getId().equals(paramsMap.get('order_id'))
        assert orderItem.getProduct().getId().equals(paramsMap.get('product_id'))
        assert orderItem.getQuantity().equals(paramsMap.get('quantity'))
    }

    def "should save order with all order items"() {
        given:
        def id =  Long.valueOf(1)

        when:
        repository.save(order)

        then:
        1 * simpleJdbcInsert.executeAndReturnKey(_ as Map) >> id

        2 * orderItemsInsert.execute(_ as Map)
    }

    def "should set id on order items before insert"() {
        given:
        def id =  Long.valueOf(33)
        simpleJdbcInsert.executeAndReturnKey(_ as Map) >> id

        when:
        repository.save(order)

        then:
        orderItems.each { assert id.equals(it.getOrder().getId())}
    }
}
