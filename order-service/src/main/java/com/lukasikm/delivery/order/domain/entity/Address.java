package com.lukasikm.delivery.order.domain.entity;

import com.lukasikm.delivery.orderserviceclient.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@AllArgsConstructor
@UserDefinedType
public class Address {
    private String street;
    private String city;
    private String postalCode;

    public String getCode() {
        return city.toUpperCase();
    }

    public AddressDTO toDto() {
        return new AddressDTO(street, city, postalCode);
    }

    public static Address of(AddressDTO addressDTO) {
        return new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getPostalCode());
    }
}
