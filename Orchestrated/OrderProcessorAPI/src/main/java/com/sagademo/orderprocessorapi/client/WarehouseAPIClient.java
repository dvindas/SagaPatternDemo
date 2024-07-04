package com.sagademo.orderprocessorapi.client;

import com.sagademo.orderprocessorapi.dto.ReserveRequestDTO;
import com.sagademo.orderprocessorapi.dto.ReserveResponseDTO;
import com.sagademo.orderprocessorapi.exception.mapper.RestClientExceptionMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "warehouseAPIClient")
@RegisterProvider(RestClientExceptionMapper.class)
public interface WarehouseAPIClient {

    @POST
    @Path("/api/inventoryReservations/reserve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    ReserveResponseDTO reserve(ReserveRequestDTO request);

}
