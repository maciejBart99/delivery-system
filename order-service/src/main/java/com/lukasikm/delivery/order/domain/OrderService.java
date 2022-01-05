package com.lukasikm.delivery.order.domain;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.lukasikm.delivery.order.domain.entity.Address;
import com.lukasikm.delivery.order.domain.entity.Order;
import com.lukasikm.delivery.order.domain.entity.OrderPhase;
import com.lukasikm.delivery.order.domain.exception.IllegalStateTransitionException;
import com.lukasikm.delivery.order.domain.exception.OrderNotFoundException;
import com.lukasikm.delivery.order.domain.repository.OrderRepository;
import com.lukasikm.delivery.orderserviceclient.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CassandraTemplate cassandraTemplate;
    @Autowired
    private TagGenerator tagGenerator;

    public List<SimpleOrderDTO> getOrders(OrderState state, UUID responsibleId, String zoneFrom, String zoneTo) {
        var builder = QueryBuilder.select().from("orders")
                .where(QueryBuilder.eq("state", state.name()));
        if (responsibleId != null) {
            builder = builder.and(QueryBuilder.eq("responsibleId", responsibleId));
        }
        if (zoneTo != null) {
            builder = builder.and(QueryBuilder.eq("zoneToCode", zoneTo));
        }
        if (zoneFrom != null) {
            builder = builder.and(QueryBuilder.eq("zoneFromCode", zoneFrom));
        }

        return cassandraTemplate.select(builder.allowFiltering(), Order.class).stream()
                .map(Order::toSimpleDto)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(Order::toDto)
                .orElseThrow(OrderNotFoundException::new);
    }

    public OrderDTO createOrder(OrderCreateDTO createDTO, UUID senderId) {
        var id = UUID.randomUUID();

        var from = Address.of(createDTO.getFrom());
        var to = Address.of(createDTO.getTo());
        var initialPhase = new OrderPhase(senderId, "", OrderState.AWAITING_PAYMENT, Instant.now());

        var order = new Order(id, senderId, senderId, createDTO.isFragile(), createDTO.getSize(), createDTO.getWeightKg(),
                from.getCode(), to.getCode(), from, to, OrderState.AWAITING_PAYMENT, List.of(initialPhase));

        orderRepository.save(order);

        return order.toDto();
    }

    public OrderDTO updateStatus(UUID orderId, OrderStateUpdateDTO stateUpdateDTO) {
        var phase = new OrderPhase(stateUpdateDTO.getResponsibleId(), stateUpdateDTO.getDescription(),
                stateUpdateDTO.getState(), Instant.now());

        return orderRepository.findById(orderId)
                .map(order -> {
                    if (order.getState().ordinal() >= stateUpdateDTO.getState().ordinal()) {
                        throw new IllegalStateTransitionException();
                    }

                    order.setResponsibleId(stateUpdateDTO.getResponsibleId());
                    order.setState(stateUpdateDTO.getState());
                    order.getHistory().add(phase);

                    orderRepository.save(order);

                    return order.toDto();
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    public byte[] generateTag(UUID orderId) {
        var order = getOrder(orderId);

        return tagGenerator.generateTag(order);
    }

    public OrderDTO pickupOrder(UUID orderId, UUID courierId) {
        var request = new OrderStateUpdateDTO("Order collected from the sender",
                OrderState.ON_WAY_TO_WAREHOUSE, courierId);

        return updateStatus(orderId, request);
    }

    public OrderDTO completeOrder(UUID orderId) {
        var request = new OrderStateUpdateDTO("Order delivered",
                OrderState.DELIVERED, null);

        return updateStatus(orderId, request);
    }
}
