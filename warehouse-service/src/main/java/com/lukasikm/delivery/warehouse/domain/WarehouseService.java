package com.lukasikm.delivery.warehouse.domain;

import com.lukasikm.delivery.warehouse.domain.entity.Warehouse;
import com.lukasikm.delivery.warehouse.domain.repositories.WarehouseRepository;
import com.lukasikm.warehouseserviceclient.dto.WarehouseCreateDTO;
import com.lukasikm.warehouseserviceclient.dto.WarehouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<WarehouseDTO> getWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(Warehouse::toDto)
                .collect(Collectors.toList());
    }

    public WarehouseDTO createWarehouse(WarehouseCreateDTO warehouseCreateDTO) {
        var warehouse = new Warehouse(UUID.randomUUID(), warehouseCreateDTO.getWarehouseName());

        warehouseRepository.save(warehouse);

        return warehouse.toDto();
    }

}
