package com.lukasikm.delivery.orderserviceclient.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class OrderStateUpdateDTO {
    @NotNull
    private final String description;
    @NotNull
    private final OrderState state;
    @NotNull
    private final UUID responsibleId;
}
