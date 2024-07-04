package com.sagademo.warehouseapi.controller.mapper;

import com.sagademo.warehouseapi.controller.dto.ReserveRequestDTO;
import com.sagademo.warehouseapi.controller.dto.ReserveResponseDTO;
import com.sagademo.warehouseapi.model.InventoryReservation;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventoryMapper {

    public InventoryReservation toEntity(ReserveRequestDTO reserveRequestDTO, String correlationId){
        return new InventoryReservation(reserveRequestDTO.orderId(), reserveRequestDTO.quantity(), correlationId);
    }

    public ReserveResponseDTO toDTO(InventoryReservation inventoryReservation) {
        return new ReserveResponseDTO(inventoryReservation.getId(), inventoryReservation.getOrderId(), inventoryReservation.getProductInventory().getProductId(), inventoryReservation.getQuantityReserved(), inventoryReservation.getStatus());
    }

}
