package com.sagademo.warehouseapi.exception;

public class NotAvailableInventoryException extends RuntimeException {

    private Error error;

    public NotAvailableInventoryException(String message) {
        super(message);
        this.error = new Error(409, 1, message);
    }

    public Error getError() {
        return error;
    }
}
