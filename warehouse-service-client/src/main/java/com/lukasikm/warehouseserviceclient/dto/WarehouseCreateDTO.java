package com.lukasikm.warehouseserviceclient.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class WarehouseCreateDTO {
    @NotNull
    private String warehouseName;
}
