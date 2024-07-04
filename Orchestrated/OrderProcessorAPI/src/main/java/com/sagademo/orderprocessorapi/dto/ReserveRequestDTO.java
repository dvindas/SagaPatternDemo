package com.sagademo.orderprocessorapi.dto;

public record ReserveRequestDTO(String orderId, String productId, int quantity){
}
