package com.sagademo.paymentapi.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO(String customerId, BigDecimal totalUSD){
}
