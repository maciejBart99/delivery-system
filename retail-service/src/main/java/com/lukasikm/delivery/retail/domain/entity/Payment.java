package com.lukasikm.delivery.retail.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@AllArgsConstructor
@UserDefinedType
public class Payment {
    private String paymentToken;
    private String paymentMethod;
}
