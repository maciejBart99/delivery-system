package com.lukasikm.delivery.orderserviceclient.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
public class OrderPhaseDTO {
    @NotBlank
    private final UUID responsibleId;
    @NotBlank
    private final String description;
    @NotNull
    private final OrderState state;
    @NotBlank
    private final Instant timestamp;
}
