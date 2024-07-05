package com.sagademo.orderprocessorapi.dto;

public record OrderPlacedResponseDTO(String orderId, String reservationId, String paymentId, String correlationLRAId){

}
