package com.lukasikm.delivery.warehouse.api;

import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.orderserviceclient.dto.SimpleOrderDTO;
import com.lukasikm.delivery.warehouse.domain.WarehouseOrdersService;
import com.lukasikm.delivery.warehouse.domain.WarehouseService;
import com.lukasikm.warehouseserviceclient.WarehouseClient;
import com.lukasikm.warehouseserviceclient.dto.LeaveOrderDTO;
import com.lukasikm.warehouseserviceclient.dto.WarehouseCreateDTO;
import com.lukasikm.warehouseserviceclient.dto.WarehouseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class WarehouseController implements WarehouseClient {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private WarehouseOrdersService warehouseOrdersService;

    @Operation(summary = "Get warehouses", tags = { "Warehouse" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @Override
    public List<WarehouseDTO> getWarehouses() {
        return warehouseService.getWarehouses();
    }

    @Operation(summary = "Create warehouse", tags = { "Warehouse" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @Override
    public WarehouseDTO createWarehouse(@RequestBody @Valid WarehouseCreateDTO warehouseCreateDTO) {
        return warehouseService.createWarehouse(warehouseCreateDTO);
    }

    @Operation(summary = "Get orders inside warehouse", tags = { "Warehouse" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Warehouse or order not found", content = @Content())
    })
    @Override
    public List<SimpleOrderDTO> getWarehouseOrders(@PathVariable("warehouseId") UUID warehouseId, String zoneToCode, String zoneFromCode) {
        return warehouseOrdersService.getWarehouseOrders(warehouseId, zoneToCode, zoneFromCode);
    }

    @Operation(summary = "Leave order at warehouse", tags = { "Warehouse" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Warehouse or order not found", content = @Content())
    })
    @Override
    public void leaveOrder(@PathVariable("warehouseId") UUID warehouseId, @RequestBody @Valid LeaveOrderDTO leaveOrderDTO) {
        warehouseOrdersService.leaveOrder(warehouseId, leaveOrderDTO);
    }

    @Operation(summary = "Collect order from warehouse", tags = { "Warehouse" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Warehouse or order not found", content = @Content())
    })
    @Override
    public void collectOrder(@PathVariable("warehouseId") UUID warehouseId, @PathVariable("orderId") UUID orderId,
                             @RequestHeader("x-auth-id") UUID xAuthId) {
        warehouseOrdersService.collectOrder(warehouseId, orderId, xAuthId);
    }
}
