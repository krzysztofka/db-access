package com.kali.dbaccess.domain;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity(name = "ORDER_ITEMS")
@IdClass(OrderItemPk.class)
public class OrderItem {

    @Id
    @ManyToOne
    @JoinColumn(name="PRODUCT_ID", referencedColumnName="ID")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name="ORDER_ID", referencedColumnName="ID")
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equal(product.getId(), orderItem.getProduct().getId()) &&
                Objects.equal(quantity, orderItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product.getId(), quantity);
    }
}
