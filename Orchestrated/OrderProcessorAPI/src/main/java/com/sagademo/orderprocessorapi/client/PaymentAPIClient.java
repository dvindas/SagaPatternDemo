package com.sagademo.orderprocessorapi.client;

import com.sagademo.orderprocessorapi.dto.PaymentRequestDTO;
import com.sagademo.orderprocessorapi.dto.PaymentResponseDTO;
import com.sagademo.orderprocessorapi.exception.mapper.RestClientExceptionMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "paymentAPIClient")
@RegisterProvider(RestClientExceptionMapper.class)
public interface PaymentAPIClient {

    @POST
    @Path("/api/paymentProcessor/process")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    PaymentResponseDTO process(PaymentRequestDTO request);

}
