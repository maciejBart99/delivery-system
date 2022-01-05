package com.lukasikm.delivery.retail.api;

import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.retail.domain.RetailService;
import com.lukasikm.delivery.retailserviceclient.RetailClient;
import com.lukasikm.delivery.retailserviceclient.dto.PaymentDTO;
import com.lukasikm.delivery.retailserviceclient.dto.RetailPriceDTO;
import com.lukasikm.delivery.retailserviceclient.dto.RetailParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class RetailController implements RetailClient {
    @Autowired
    private RetailService retailService;

    @Operation(summary = "Start retail process and estimate price", tags = { "Retail" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @Override
    public RetailPriceDTO startRetailProcess(@Valid @RequestBody RetailParametersDTO priceEstimateRequestDTO) {
        return retailService.startRetailProcess(priceEstimateRequestDTO);
    }

    @Operation(summary = "Confirm retail process", tags = { "Retail" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "409", description = "Retail process already confirmed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Retail process not found", content = @Content())
    })
    @Override
    public OrderDTO purchase(@PathVariable("retailId") UUID retailId, @RequestHeader("x-auth-id") String xAuthId) {
        return retailService.confirmRetail(retailId, UUID.fromString(xAuthId));
    }

    @Operation(summary = "Payment provider confirms money received", tags = { "Retail" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid payment token", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Retail process not found", content = @Content())
    })
    @Override
    public void confirmPayment(@PathVariable("retailId") UUID retailId, PaymentDTO payment) {
        retailService.confirmPayment(payment, retailId);
    }
}
