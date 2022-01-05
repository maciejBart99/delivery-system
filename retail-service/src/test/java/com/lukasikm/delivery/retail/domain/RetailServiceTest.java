package com.lukasikm.delivery.retail.domain;

import com.lukasikm.delivery.orderserviceclient.OrdersClient;
import com.lukasikm.delivery.orderserviceclient.dto.AddressDTO;
import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.orderserviceclient.dto.OrderState;
import com.lukasikm.delivery.orderserviceclient.dto.SizeClass;
import com.lukasikm.delivery.retail.domain.entity.Address;
import com.lukasikm.delivery.retail.domain.entity.Retail;
import com.lukasikm.delivery.retail.domain.repository.RetailRepository;
import com.lukasikm.delivery.retailserviceclient.dto.RetailParametersDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class RetailServiceTest {
    @Mock
    private RetailRepository retailRepository;
    @Mock
    private PriceService priceService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private OrdersClient ordersClient;
    @InjectMocks
    private RetailService retailService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCallPriceServiceAndSaveRetailWhenProcessIsStarted() {
        // given
        var price = new Price(10, "EUR");
        var params = new RetailParametersDTO(SizeClass.LARGE, true, 1.2,
                new AddressDTO("a", "a", "a"), new AddressDTO("a", "a", "a"));

        Mockito.when(priceService.estimatePrice(params)).thenReturn(price);

        // when
        var result = retailService.startRetailProcess(params);

        // when
        assertEquals(price.getPrice(), result.getPrice());
        assertEquals(price.getCurrency(), result.getCurrency());
        Mockito.verify(priceService, Mockito.times(1)).estimatePrice(params);
        Mockito.verify(retailRepository, Mockito.times(1)).save(any());
    }

    @Test
    void shouldCallCreateOrderAdnCallPaymentServiceWhenRetailConfirmed() {
        // given
        var retail = new Retail(UUID.randomUUID(), UUID.randomUUID(), SizeClass.LARGE, true, Instant.now(), 1.2,
                new Address("a", "a", "a"), new Address("a", "a", "a"), 10, "EUR", null);
        var order = new OrderDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), true, SizeClass.LARGE, 1.2,
                new AddressDTO("a", "a", "a"), new AddressDTO("a", "a", "a"),
                "A", "B", OrderState.AWAITING_COLLECTION, List.of());
        var userId = UUID.randomUUID();

        Mockito.when(retailRepository.findById(retail.getId())).thenReturn(Optional.of(retail));
        Mockito.when(ordersClient.createOrder(any(), anyString())).thenReturn(order);
        Mockito.when(paymentService.sendPaymentRequest(anyByte(), anyString(), any())).thenReturn("token");

        // when
        var result = retailService.confirmRetail(retail.getId(), userId);

        // when
        assertEquals(order, result);
        Mockito.verify(ordersClient, Mockito.times(1)).createOrder(any(), anyString());
        Mockito.verify(paymentService, Mockito.times(1)).sendPaymentRequest(eq(10d), eq("EUR"), any());
    }


}