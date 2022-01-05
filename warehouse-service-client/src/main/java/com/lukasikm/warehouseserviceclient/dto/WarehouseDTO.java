package com.lukasikm.warehouseserviceclient.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WarehouseDTO {
    private final UUID id;
    private final String name;
}
