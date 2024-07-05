package com.sagademo.paymentapi.exception.mapper;

import com.sagademo.paymentapi.exception.InsufficientFundsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientFundsExceptionMapper implements ExceptionMapper<InsufficientFundsException> {

    @Override
    public Response toResponse(InsufficientFundsException exception) {
        return Response.status(exception.getError().statusCode())
                .entity(exception.getError())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
