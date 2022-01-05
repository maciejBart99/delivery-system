package com.lukasikm.delivery.order.api;

import com.lukasikm.delivery.order.domain.OrderService;
import com.lukasikm.delivery.orderserviceclient.OrdersClient;
import com.lukasikm.delivery.orderserviceclient.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController implements OrdersClient {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all order with given criteria", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @Override
    public List<SimpleOrderDTO> getOrdersForUser(OrderState state, @RequestParam(value = "responsibleId", required = false) UUID responsibleId,
                                                 @RequestParam(required = false) String zoneToCode, @RequestParam(required = false) String zoneFromCode) {
        return orderService.getOrders(state, responsibleId, zoneFromCode, zoneToCode);
    }

    @Operation(summary = "Get order by id", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content())
    })
    @Override
    public OrderDTO getOrder(@PathVariable("orderId") UUID orderId) {
        return orderService.getOrder(orderId);
    }

    @Operation(summary = "Generate order tag", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content())
    })
    @Override
    public byte[] getOrderTag(@PathVariable("orderId") UUID orderId) {
        return orderService.generateTag(orderId);
    }

    @Operation(summary = "Create new order", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @Override
    public OrderDTO createOrder(@RequestBody @Valid OrderCreateDTO createDTO, @RequestHeader("x-auth-id") String xAuthId) {
        return orderService.createOrder(createDTO, UUID.fromString(xAuthId));
    }

    @Operation(summary = "Pickup order from sender", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content())
    })
    @Override
    public OrderDTO pickupOrder(@PathVariable("orderId") UUID orderId, @RequestHeader("x-auth-id") String xAuthId) {
        return orderService.pickupOrder(orderId, UUID.fromString(xAuthId));
    }

    @Operation(summary = "Confirm that order was delivered", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content())
    })
    @Override
    public OrderDTO completeOrder(@PathVariable("orderId") UUID orderId) {
        return orderService.completeOrder(orderId);
    }

    @Operation(summary = "Update order status", tags = { "Orders" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "409", description = "Illegal state transition", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content())
    })
    @Override
    public OrderDTO updateStatus(@PathVariable("orderId") UUID orderId, @RequestBody @Valid OrderStateUpdateDTO updateDTO) {
        return orderService.updateStatus(orderId, updateDTO);
    }
}
