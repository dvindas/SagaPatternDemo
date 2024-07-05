package com.sagademo.orderapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderCreationRequestDTO(@NotBlank String customerId, @NotNull @Valid ItemDTO item) {
}
