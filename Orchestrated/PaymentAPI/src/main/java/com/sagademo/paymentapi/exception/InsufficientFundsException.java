package com.sagademo.paymentapi.exception;

public class InsufficientFundsException extends RuntimeException {

    private Error error;

    public InsufficientFundsException(String message) {
        super(message);
        this.error = new Error(402, 2, message);
    }

    public Error getError() {
        return error;
    }

}
