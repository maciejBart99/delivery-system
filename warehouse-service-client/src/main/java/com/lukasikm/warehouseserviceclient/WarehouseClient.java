package com.lukasikm.warehouseserviceclient;

import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.orderserviceclient.dto.SimpleOrderDTO;
import com.lukasikm.warehouseserviceclient.dto.LeaveOrderDTO;
import com.lukasikm.warehouseserviceclient.dto.WarehouseCreateDTO;
import com.lukasikm.warehouseserviceclient.dto.WarehouseDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("warehouse-service")
@RibbonClient("warehouse-service")
public interface WarehouseClient {
    @GetMapping("/api/warehouse")
    List<WarehouseDTO> getWarehouses();
    @PostMapping("/api/warehouse")
    WarehouseDTO createWarehouse(@RequestBody WarehouseCreateDTO warehouseCreateDTO);
    @GetMapping("/api/warehouse/{warehouseId}/orders")
    List<SimpleOrderDTO> getWarehouseOrders(@PathVariable("warehouseId") UUID warehouseId,
                                               @RequestParam(required = false) String zoneToCode,
                                               @RequestParam(required = false) String zoneFromCode);
    @PostMapping("/api/warehouse/{warehouseId}/orders")
    void leaveOrder(@PathVariable("warehouseId") UUID warehouseId, LeaveOrderDTO leaveOrderDTO);
    @PostMapping("/api/warehouse/{warehouseId}/orders/{orderId}/collect")
    void collectOrder(@PathVariable("warehouseId") UUID warehouseId, @PathVariable("orderId") UUID orderId,
                      @RequestHeader("x-auth-id") UUID xAuthId);
}
