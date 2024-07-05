package com.sagademo.orderapi.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigDecimal;

@ApplicationScoped
public class AppConfig {

    @Inject
    @ConfigProperty(name = "app.pricing.vat-percent")
    private BigDecimal vatPercent;
    @Inject
    @ConfigProperty(name = "app.pricing.flat-rate-shipping-usd")
    private BigDecimal flatRateShippingUSD;

    public BigDecimal getVatPercent() {
        return vatPercent;
    }

    public BigDecimal getFlatRateShippingUSD() {
        return flatRateShippingUSD;
    }
}


