package com.lukasikm.delivery.orderserviceclient.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class OrderFiltersDTO {
    @NotNull
    private final OrderState state;
    private final UUID responsibleId;
    private final String zoneToCode;
    private final String zoneFromCode;
}
