package com.lukasikm.delivery.warehouse.domain.entity;

import com.lukasikm.warehouseserviceclient.dto.WarehouseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Getter
@Setter
@AllArgsConstructor
public class Warehouse {
    @PrimaryKey
    private UUID id;
    private String name;

    public WarehouseDTO toDto() {
        return new WarehouseDTO(id, name);
    }
}
