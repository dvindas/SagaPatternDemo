package com.sagademo.warehouseapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "ProductInventory")
@Table(name = "PRODUCT_INVENTORIES")
@NamedQueries({
        @NamedQuery(name = "ProductInventory.findByProductId",
                query = "SELECT p FROM ProductInventory p WHERE p.productId = :productId")
})
public class ProductInventory  {

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
    @OneToMany(mappedBy = "productInventory", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InventoryReservation> reservations;
    @Version
    @Basic(optional = false)
    @Column(name = "VERSION")
    private long version;

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

    public List<InventoryReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<InventoryReservation> reservations) {
        this.reservations = reservations;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
