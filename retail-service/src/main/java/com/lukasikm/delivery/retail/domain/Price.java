package com.lukasikm.delivery.retail.domain;

import lombok.Data;

@Data
public class Price {
    private final double price;
    private final String currency;
}
