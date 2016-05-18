package com.kali.dbaccess.domain;

import com.google.common.base.Objects;
import java.io.Serializable;

public class OrderItemPk implements Serializable {

    private Long order;

    private Long product;

    public OrderItemPk() {
    }

    public OrderItemPk(Long orderId, Long productId) {
        this.order = orderId;
        this.product = productId;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long orderId) {
        this.order = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPk that = (OrderItemPk) o;
        return Objects.equal(order, that.order) &&
                Objects.equal(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(order, product);
    }
}
