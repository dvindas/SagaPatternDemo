package com.sagademo.warehouseapi.controller.dto;

public record ReserveRequestDTO(String orderId, String productId, int quantity){
}
