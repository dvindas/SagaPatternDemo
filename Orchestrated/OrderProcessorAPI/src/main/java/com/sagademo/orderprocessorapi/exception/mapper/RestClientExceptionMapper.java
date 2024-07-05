package com.sagademo.orderprocessorapi.exception.mapper;

import com.sagademo.orderprocessorapi.exception.APIResponseException;
import com.sagademo.orderprocessorapi.exception.Error;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class RestClientExceptionMapper  implements ResponseExceptionMapper<RuntimeException> {

    @Override
    public RuntimeException toThrowable(Response response) {
        Error error;
        try {
            error = response.readEntity(Error.class);
        } catch (Exception ex) {
            error = new Error(response.getStatus(), -1, "Unexpected error " + ex.getMessage());
        }

        throw new APIResponseException("Failed to process request. HTTP status: " + response.getStatus(), error);
    }

}
