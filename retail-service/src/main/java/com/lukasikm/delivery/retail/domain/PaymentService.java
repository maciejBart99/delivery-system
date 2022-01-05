package com.lukasikm.delivery.retail.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public String sendPaymentRequest(double price, String currency, UUID retailId) {
        var token = UUID.randomUUID();

        // send request here to external payment api
        logger.info("The payment token is {}", token);

        return token.toString();
    }
}
