package com.sagademo.warehouseapi.dto;

public record ReserveResponseDTO(String id, String orderId, String productId, int quantity, String status) {
}
