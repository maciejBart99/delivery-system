package com.lukasikm.delivery.orderserviceclient.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderCreateDTO {
    private final boolean fragile;
    @NotNull
    private final SizeClass size;
    private final double weightKg;
    @NotNull
    private final AddressDTO from;
    @NotNull
    private final AddressDTO to;
}
