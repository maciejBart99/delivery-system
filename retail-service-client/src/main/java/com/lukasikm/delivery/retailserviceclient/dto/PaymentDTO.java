package com.lukasikm.delivery.retailserviceclient.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private final String paymentToken;
    private final String paymentMethod;
}
