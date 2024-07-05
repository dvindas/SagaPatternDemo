package com.sagademo.orderprocessorapi.exception.mapper;

import com.sagademo.orderprocessorapi.exception.APIResponseException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class APIResponseExceptionMapper implements ExceptionMapper<APIResponseException> {

    @Override
    public Response toResponse(APIResponseException exception) {
        return Response.status(exception.getError().statusCode())
                .entity(exception.getError())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
