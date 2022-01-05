package com.lukasikm.delivery.retailserviceclient.dto;

import com.lukasikm.delivery.orderserviceclient.dto.AddressDTO;
import com.lukasikm.delivery.orderserviceclient.dto.SizeClass;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RetailParametersDTO {
    @NotNull
    private final SizeClass size;
    private final boolean isFragile;
    private final double weight;
    @NotNull
    private final AddressDTO from;
    @NotNull
    private final AddressDTO to;
}
