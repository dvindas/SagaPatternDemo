package com.sagademo.paymentapi.service;

import com.sagademo.paymentapi.exception.InsufficientFundsException;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
public class PaymentProcessorService {

    public String process(String customerId, BigDecimal amount){
        // Dummy logic, just for testing
        if(customerId.equals("c382f61d-3e6e-4abd-a6d9-99d691a90847")){
            throw new InsufficientFundsException("Insufficient funds for this transaction.");
        }

        var paymentId = UUID.randomUUID().toString();
        return paymentId;
    }

    public void refund(String lraid){}

}
