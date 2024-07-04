package com.sagademo.warehouseapi.exception;

public record Error(int statusCode, int errorCode, String message) {
}
