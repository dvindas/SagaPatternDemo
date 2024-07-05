package com.sagademo.orderapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemDTO(String id, @NotBlank String productId, @Positive BigDecimal price, @Positive int quantity) {
}