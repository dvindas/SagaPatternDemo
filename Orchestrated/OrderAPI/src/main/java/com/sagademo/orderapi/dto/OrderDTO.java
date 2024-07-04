package com.sagademo.orderapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(String id, String customerId, BigDecimal shippingCostUSD, BigDecimal taxAmountUSD,
                       BigDecimal subTotalUSD, BigDecimal totalUSD, LocalDateTime creationDate, String status,
                       String correlationId, ItemDTO item) {
}
