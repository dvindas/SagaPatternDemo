package com.sagademo.orderprocessorapi.exception;

public record Error(int statusCode, int errorCode, String message) {
}
