package com.sagademo.orderprocessorapi.exception;

public class APIResponseException extends RuntimeException {

    private final Error error;

    public APIResponseException(String message, Error error) {
        super(message);
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
