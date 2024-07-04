package com.sagademo.orderprocessorapi.service;

import com.sagademo.orderprocessorapi.client.OrderAPIClient;
import com.sagademo.orderprocessorapi.client.PaymentAPIClient;
import com.sagademo.orderprocessorapi.client.WarehouseAPIClient;
import com.sagademo.orderprocessorapi.dto.OrderCreationRequestDTO;
import com.sagademo.orderprocessorapi.dto.OrderPlacedResponseDTO;
import com.sagademo.orderprocessorapi.dto.PaymentRequestDTO;
import com.sagademo.orderprocessorapi.dto.ReserveRequestDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class OrderProcessorService {

    private final OrderAPIClient orderAPIClient;
    private final WarehouseAPIClient warehouseAPIClient;
    private final PaymentAPIClient paymentAPIClient;

    @Inject
    public OrderProcessorService(@RestClient OrderAPIClient orderAPIClient,
                                 @RestClient WarehouseAPIClient warehouseAPIClient,
                                 @RestClient PaymentAPIClient paymentAPIClient) {
        this.orderAPIClient = orderAPIClient;
        this.warehouseAPIClient = warehouseAPIClient;
        this.paymentAPIClient = paymentAPIClient;
    }

    public OrderPlacedResponseDTO process(OrderCreationRequestDTO orderCreationRequestDTO, String lraId) {
        var orderResponse = orderAPIClient.createOrder(orderCreationRequestDTO);
        var reserveRequest = new ReserveRequestDTO(orderResponse.id(), orderCreationRequestDTO.item().productId(),
                orderCreationRequestDTO.item().quantity());
        var reserveResponse = warehouseAPIClient.reserve(reserveRequest);
        var paymentRequest = new PaymentRequestDTO(orderCreationRequestDTO.customerId(), orderResponse.totalUSD());
        var paymentResponse = paymentAPIClient.process(paymentRequest);

        return new OrderPlacedResponseDTO(orderResponse.id(), reserveResponse.id(), paymentResponse.id(), lraId);
    }

}

