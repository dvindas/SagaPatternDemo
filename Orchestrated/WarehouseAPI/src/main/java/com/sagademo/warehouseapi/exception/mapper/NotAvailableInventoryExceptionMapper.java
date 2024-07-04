package com.sagademo.warehouseapi.exception.mapper;

import com.sagademo.warehouseapi.exception.NotAvailableInventoryException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAvailableInventoryExceptionMapper implements ExceptionMapper<NotAvailableInventoryException> {

    @Override
    public Response toResponse(NotAvailableInventoryException exception) {
        return Response.status(exception.getError().statusCode())
                .entity(exception.getError())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
