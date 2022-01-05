package com.lukasikm.delivery.orderserviceclient;

import com.lukasikm.delivery.orderserviceclient.dto.*;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("order-service")
@RibbonClient("order-service")
public interface OrdersClient {
    @GetMapping("/api/orders")
    List<SimpleOrderDTO> getOrdersForUser(@RequestParam OrderState state, @RequestParam("responsibleId") UUID responsibleId,
                                    @RequestParam(required = false) String zoneToCode,  @RequestParam(required = false) String zoneFromCode);

    @GetMapping("/api/orders/{orderId}")
    OrderDTO getOrder(@PathVariable("orderId") UUID orderId);

    @GetMapping("/api/orders/{orderId}/tag")
    byte[] getOrderTag(@PathVariable("orderId") UUID orderId);

    @PostMapping("/api/orders")
    OrderDTO createOrder(@RequestBody OrderCreateDTO createDTO, @RequestHeader("x-auth-id") String xAuthId);

    @PostMapping("/api/orders/{orderId}/pickup")
    OrderDTO pickupOrder(@PathVariable("orderId") UUID orderId, @RequestHeader("x-auth-id") String xAuthId);

    @PostMapping("/api/orders/{orderId}/complete")
    OrderDTO completeOrder(@PathVariable("orderId") UUID orderId);

    @PutMapping("/api/orders/{orderId}/status")
    OrderDTO updateStatus(@PathVariable("orderId") UUID orderId, @RequestBody OrderStateUpdateDTO createDTO);
}
