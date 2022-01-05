package com.lukasikm.delivery.order.domain.entity;

import com.lukasikm.delivery.orderserviceclient.dto.OrderPhaseDTO;
import com.lukasikm.delivery.orderserviceclient.dto.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@UserDefinedType
public class OrderPhase {
    private UUID responsibleId;
    private String description;
    private OrderState state;
    private Instant timestamp;

    public OrderPhaseDTO toDto() {
        return new OrderPhaseDTO(responsibleId, description, state, timestamp);
    }
}
