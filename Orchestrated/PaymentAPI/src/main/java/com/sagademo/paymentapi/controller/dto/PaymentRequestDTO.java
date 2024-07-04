package com.sagademo.paymentapi.controller.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO(String customerId, BigDecimal totalUSD){
}
