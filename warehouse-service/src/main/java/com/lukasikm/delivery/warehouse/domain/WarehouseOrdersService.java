package com.lukasikm.delivery.warehouse.domain;

import com.lukasikm.delivery.orderserviceclient.OrdersClient;
import com.lukasikm.delivery.orderserviceclient.dto.*;
import com.lukasikm.delivery.warehouse.domain.exceptions.WarehouseNotFoundException;
import com.lukasikm.delivery.warehouse.domain.repositories.WarehouseRepository;
import com.lukasikm.warehouseserviceclient.dto.LeaveOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WarehouseOrdersService {

    @Autowired
    private OrdersClient ordersClient;
    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<SimpleOrderDTO> getWarehouseOrders(UUID warehouseId, String zoneToCode, String zoneFromCode) {
        warehouseRepository.findById(warehouseId).orElseThrow(WarehouseNotFoundException::new);

        return ordersClient.getOrdersForUser(OrderState.WAREHOUSE, warehouseId, zoneToCode, zoneFromCode);
    }

    public void leaveOrder(UUID warehouseId, LeaveOrderDTO leaveOrderDTO) {
        warehouseRepository.findById(warehouseId).orElseThrow(WarehouseNotFoundException::new);

        var orderUpdateRequest = new OrderStateUpdateDTO("Order left at the warehouse",
                OrderState.WAREHOUSE, warehouseId);

        ordersClient.updateStatus(leaveOrderDTO.getOrderId(), orderUpdateRequest);
    }

    public void collectOrder(UUID warehouseId, UUID orderId, UUID responsibleId) {
        warehouseRepository.findById(warehouseId).orElseThrow(WarehouseNotFoundException::new);

        var orderUpdateRequest = new OrderStateUpdateDTO("Order collected from the warehouse",
                OrderState.ON_WAY_TO_DESTINATION, responsibleId);

        ordersClient.updateStatus(orderId, orderUpdateRequest);
    }
}
