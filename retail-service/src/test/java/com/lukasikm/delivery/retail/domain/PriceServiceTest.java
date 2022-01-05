package com.lukasikm.delivery.retail.domain;

import com.lukasikm.delivery.orderserviceclient.dto.AddressDTO;
import com.lukasikm.delivery.orderserviceclient.dto.SizeClass;
import com.lukasikm.delivery.retailserviceclient.dto.RetailParametersDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PriceServiceTest {
    @Test
    void shouldUseEurAsCurrency() {
        // given
        var priceService = new PriceService();
        var params = new RetailParametersDTO(SizeClass.LARGE, true, 1.2,
                new AddressDTO("a", "a", "a"), new AddressDTO("a", "a", "a"));

        // when
        var price = priceService.estimatePrice(params);

        // then
        assertEquals("EUR", price.getCurrency());
    }

    @Test
    void shouldCalculateCorrectPriceWithWeightAndSize() {
        // given
        var priceService = new PriceService();
        var params = new RetailParametersDTO(SizeClass.LARGE, false, 12,
                new AddressDTO("a", "a", "a"), new AddressDTO("a", "a", "a"));

        // when
        var price = priceService.estimatePrice(params);

        // then
        assertEquals(36d, price.getPrice());
    }

    @Test
    void shouldDoubleThePriceForFragileOrders() {
        // given
        var priceService = new PriceService();
        var params = new RetailParametersDTO(SizeClass.LARGE, true, 12,
                new AddressDTO("a", "a", "a"), new AddressDTO("a", "a", "a"));

        // when
        var price = priceService.estimatePrice(params);

        // then
        assertEquals(72d, price.getPrice());
    }
}