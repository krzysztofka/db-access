package com.kali.dbaccess.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;
import java.util.Set;

public class Order implements Entity<Long>  {

    private Long id;

    private Customer customer;

    private Set<OrderItem> items;

    private Date orderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(id, order.id)
                .append(customer, order.customer)
                .append(orderDate, order.orderDate)
                .append(items, order.items)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(customer)
                .append(orderDate)
                .toHashCode();
    }
}
