package com.sagademo.warehouseapi.model;

import jakarta.persistence.*;

@Entity(name = "InventoryReservation")
@Table(name = "INVENTORY_RESERVATIONS", indexes = {
        @Index(name = "idx_correlationId", columnList = "CORRELATION_ID")
})
@NamedQueries({
        @NamedQuery(name = "InventoryReservation.findAll",
                query = "SELECT r FROM InventoryReservation r"),
        @NamedQuery(name = "InventoryReservation.findByCorrelationId",
                query = "SELECT r FROM InventoryReservation r WHERE r.correlationId = :correlationId")
})
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID", nullable = false)
    private String orderId;
    @Basic(optional = false)
    @Column(name = "QUANTITY_RESERVED", nullable = false)
    private int quantityReserved;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INVENTORY_ID")
    private ProductInventory productInventory;
    @Basic(optional = false)
    @Column(name = "STATUS", nullable = false)
    private String status;
    @Basic(optional = false)
    @Column(name = "CORRELATION_ID", nullable = false, unique = true)
    private String correlationId;
    @Version
    @Column(name = "VERSION")
    private long version;

    public InventoryReservation() {}

    public InventoryReservation(String orderId, int quantityReserved, String correlationId) {
        this.orderId = orderId;
        this.quantityReserved = quantityReserved;
        this.status = "PENDING_TO_CONFIRM";
        this.correlationId = correlationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(int quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public ProductInventory getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
