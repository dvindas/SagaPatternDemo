package com.sagademo.orderapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Order")
@Table(name = "ORDERS", indexes = {
        @Index(name = "idx_correlationId", columnList = "CORRELATION_ID")
})
@NamedQueries({
        @NamedQuery(name = "Order.findAll",
                query = "SELECT p FROM Order p order by p.creationDate"),
        @NamedQuery(name = "Order.findByCorrelationId",
                query = "SELECT p FROM Order p WHERE p.correlationId = :correlationId")
})
@NamedEntityGraph(
        name = "Order.details",
        attributeNodes = @NamedAttributeNode("details")
)
public class Order {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Basic(optional = false)
    @Column(name = "CUSTOMER_ID", nullable = false, updatable = false)
    private String customerId;
    @Basic(optional = false)
    @Column(name = "SHIPPING_COST_USD", nullable = false)
    private BigDecimal shippingCostUSD;
    @Basic(optional = false)
    @Column(name = "TAX_AMOUNT_USD", nullable = false)
    private BigDecimal taxAmountUSD;
    @Basic(optional = false)
    @Column(name = "SUB_TOTAL_USD", nullable = false)
    private BigDecimal subTotalUSD;
    @Basic(optional = false)
    @Column(name = "TOTAL_USD", nullable = false)
    private BigDecimal totalUSD;
    @Basic(optional = false)
    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;
    @OneToMany(mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> details;
    @Basic(optional = false)
    @Column(name = "STATUS", nullable = false)
    private String status;
    @Basic(optional = false)
    @Column(name = "CORRELATION_ID", nullable = false, unique = true)
    private String correlationId;


    @PrePersist
    protected void onCreate() {
        if(creationDate == null) {
            this.creationDate = LocalDateTime.now();
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTaxAmountUSD() {
        return taxAmountUSD;
    }

    public void setTaxAmountUSD(BigDecimal taxAmountUSD) {
        this.taxAmountUSD = taxAmountUSD;
    }

    public BigDecimal getShippingCostUSD() {
        return shippingCostUSD;
    }

    public void setShippingCostUSD(BigDecimal shippingCostUSD) {
        this.shippingCostUSD = shippingCostUSD;
    }

    public BigDecimal getTotalUSD() {
        return totalUSD;
    }

    public void setTotalUSD(BigDecimal totalUSD) {
        this.totalUSD = totalUSD;
    }

    public BigDecimal getSubTotalUSD() {
        return subTotalUSD;
    }

    public void setSubTotalUSD(BigDecimal subTotalUSD) {
        this.subTotalUSD = subTotalUSD;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
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
}
