package com.sagademo.orderprocessorapi.client;

import com.sagademo.orderprocessorapi.dto.OrderCreationRequestDTO;
import com.sagademo.orderprocessorapi.dto.OrderCreationResponseDTO;
import com.sagademo.orderprocessorapi.exception.mapper.RestClientExceptionMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "orderAPIClient")
@RegisterProvider(RestClientExceptionMapper.class)
public interface OrderAPIClient {

    @POST
    @Path("/api/orders/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    OrderCreationResponseDTO createOrder(OrderCreationRequestDTO request);

}
