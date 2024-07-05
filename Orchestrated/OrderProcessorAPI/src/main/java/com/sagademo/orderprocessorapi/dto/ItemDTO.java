package com.sagademo.orderprocessorapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemDTO(@NotBlank String productId, @Positive int quantity, @Positive BigDecimal price) {
}