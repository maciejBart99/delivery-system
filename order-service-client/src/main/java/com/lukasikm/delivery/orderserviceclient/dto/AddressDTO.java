package com.lukasikm.delivery.orderserviceclient.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDTO {
    @NotBlank
    private final String street;
    @NotBlank
    private final String city;
    @NotBlank
    private final String postalCode;
}
