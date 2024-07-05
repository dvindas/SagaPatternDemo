package com.sagademo.orderapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "OrderDetail")
@Table(name = "ORDERS_DETAILS")
public class OrderDetail {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID", nullable = false, updatable = false)
    private String productId;
    @Basic(optional = false)
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
    @Basic(optional = false)
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
