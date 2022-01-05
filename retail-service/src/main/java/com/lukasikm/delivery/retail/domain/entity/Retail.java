package com.lukasikm.delivery.retail.domain.entity;

import com.lukasikm.delivery.orderserviceclient.dto.AddressDTO;
import com.lukasikm.delivery.orderserviceclient.dto.SizeClass;
import com.lukasikm.delivery.retailserviceclient.dto.RetailPriceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table
@Getter
@Setter
@AllArgsConstructor
public class Retail {
    @PrimaryKey
    private UUID id;
    private UUID orderId;
    private SizeClass size;
    private boolean isFragile;
    private Instant created;
    private double weight;
    private Address fromAddress;
    private Address toAddress;
    private double price;
    private String currency;
    private Payment payment;

    public RetailPriceDTO toPriceDTO() {
        return new RetailPriceDTO(id, price, currency);
    }
}
