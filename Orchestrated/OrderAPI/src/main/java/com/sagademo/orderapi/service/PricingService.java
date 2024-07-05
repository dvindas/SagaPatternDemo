package com.sagademo.orderapi.service;

import com.sagademo.orderapi.config.AppConfig;
import com.sagademo.orderapi.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@ApplicationScoped
public class PricingService {

    private final AppConfig appConfig;

    @Inject
    public PricingService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * This example illustrates a basic implementation of price calculation logic, a dummy logic just for the example
     *
     * @param order Placed order
     */
    public void calculate(Order order) {
        final var subTotal = order.getDetails().stream()
                .map(d -> d.getPrice().multiply(BigDecimal.valueOf(d.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setSubTotalUSD(subTotal);

        final var taxAmount = subTotal.multiply(appConfig.getVatPercent());
        order.setTaxAmountUSD(taxAmount);

        final var shippingCost = appConfig.getFlatRateShippingUSD();
        order.setShippingCostUSD(shippingCost);

        final var total = subTotal.add(taxAmount).add(shippingCost);
        order.setTotalUSD(total);
    }

}
