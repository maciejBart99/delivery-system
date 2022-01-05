package com.lukasikm.delivery.order.domain.entity;

import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.orderserviceclient.dto.OrderState;
import com.lukasikm.delivery.orderserviceclient.dto.SimpleOrderDTO;
import com.lukasikm.delivery.orderserviceclient.dto.SizeClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Table("orders")
@Getter
@Setter
@AllArgsConstructor
public class Order {
    @PrimaryKey
    private UUID id;
    private UUID senderId;
    @Indexed
    private UUID responsibleId;
    private boolean fragile;
    private SizeClass size;
    private double weightKg;
    @Indexed
    private String zoneFromCode;
    @Indexed
    private String zoneToCode;
    private Address addressFrom;
    private Address addressTo;
    @Indexed
    private OrderState state;
    private List<OrderPhase> history;

    public OrderDTO toDto() {
        var phases = history.stream()
                .map(OrderPhase::toDto)
                .collect(Collectors.toList());

        return new OrderDTO(id, responsibleId, senderId, fragile, size, weightKg,
                addressFrom.toDto(), addressTo.toDto(), addressFrom.getCode(), addressTo.getCode(),
                state, phases);
    }

    public SimpleOrderDTO toSimpleDto() {
        return new SimpleOrderDTO(id, responsibleId, fragile, size, weightKg,
                addressFrom.toDto(), addressTo.toDto(), addressFrom.getCode(), addressTo.getCode(),
                state);
    }
}
