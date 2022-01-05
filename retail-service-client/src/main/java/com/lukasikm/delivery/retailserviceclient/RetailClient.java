package com.lukasikm.delivery.retailserviceclient;

import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.retailserviceclient.dto.PaymentDTO;
import com.lukasikm.delivery.retailserviceclient.dto.RetailPriceDTO;
import com.lukasikm.delivery.retailserviceclient.dto.RetailParametersDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient("retail-service")
@RibbonClient("retail-service")
public interface RetailClient {
    @PostMapping("/api/retail")
    RetailPriceDTO startRetailProcess(@RequestBody RetailParametersDTO priceEstimateRequestDTO);

    @PostMapping("/api/retail/{retailId}/purchase")
    OrderDTO purchase(@PathVariable("retailId") UUID retailId, @RequestHeader("x-auth-id") String xAuthId);

    @PostMapping("/api/retail/{retailId}/payment")
    void confirmPayment(@PathVariable("retailId") UUID retailId, @RequestBody PaymentDTO payment);
}
