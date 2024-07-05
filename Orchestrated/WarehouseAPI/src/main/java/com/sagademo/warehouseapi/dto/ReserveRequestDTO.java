package com.sagademo.warehouseapi.dto;

public record ReserveRequestDTO(String orderId, String productId, int quantity){
}
