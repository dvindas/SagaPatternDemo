package com.sagademo.orderprocessorapi.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO(String customerId, BigDecimal totalUSD){
}
