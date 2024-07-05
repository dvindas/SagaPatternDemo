package com.sagademo.paymentapi.exception;

public record Error(int statusCode, int errorCode, String message) {
}
