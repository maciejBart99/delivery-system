package com.lukasikm.delivery.retailserviceclient.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RetailPriceDTO {
    private final UUID id;
    private final double price;
    private final String currency;
}
