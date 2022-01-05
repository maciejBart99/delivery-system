package com.lukasikm.delivery.orderserviceclient.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SimpleOrderDTO {
    @NotNull
    private final UUID id;
    @NotNull
    private final UUID responsibleId;
    private final boolean fragile;
    @NotNull
    private final SizeClass size;
    private final double weightKg;
    @NotNull
    private final AddressDTO from;
    @NotNull
    private final AddressDTO to;
    @NotNull
    private final String zoneFromCode;
    @NotNull
    private final String zoneToCode;
    @NotNull
    private final OrderState state;
}
