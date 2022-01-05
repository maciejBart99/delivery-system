package com.lukasikm.delivery.retail.domain;

import com.lukasikm.delivery.orderserviceclient.OrdersClient;
import com.lukasikm.delivery.orderserviceclient.dto.OrderCreateDTO;
import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import com.lukasikm.delivery.orderserviceclient.dto.OrderState;
import com.lukasikm.delivery.orderserviceclient.dto.OrderStateUpdateDTO;
import com.lukasikm.delivery.retail.domain.entity.Address;
import com.lukasikm.delivery.retail.domain.entity.Payment;
import com.lukasikm.delivery.retail.domain.entity.Retail;
import com.lukasikm.delivery.retail.domain.exceptions.InvalidPaymentTokenException;
import com.lukasikm.delivery.retail.domain.exceptions.RetailAlreadyConfirmed;
import com.lukasikm.delivery.retail.domain.exceptions.RetailNotFound;
import com.lukasikm.delivery.retail.domain.repository.RetailRepository;
import com.lukasikm.delivery.retailserviceclient.dto.PaymentDTO;
import com.lukasikm.delivery.retailserviceclient.dto.RetailParametersDTO;
import com.lukasikm.delivery.retailserviceclient.dto.RetailPriceDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RetailService {

    private final RetailRepository retailRepository;
    private final PriceService priceService;
    private final PaymentService paymentService;
    private final OrdersClient ordersClient;

    @Transactional
    public RetailPriceDTO startRetailProcess(RetailParametersDTO retailParameters) {
        var price = priceService.estimatePrice(retailParameters);

        var retailEntity = new Retail(UUID.randomUUID(), null, retailParameters.getSize(), retailParameters.isFragile(),
                Instant.now(), retailParameters.getWeight(), Address.of(retailParameters.getFrom()),
                Address.of(retailParameters.getTo()), price.getPrice(), price.getCurrency(), null);

        retailRepository.save(retailEntity);

        return retailEntity.toPriceDTO();
    }

    @Transactional
    public OrderDTO confirmRetail(UUID retailId, UUID userId) {
        var retail = retailRepository.findById(retailId)
                .orElseThrow(RetailNotFound::new);

        if (retail.getPayment() != null) {
            throw new RetailAlreadyConfirmed();
        }

        var orderCreateRequest = new OrderCreateDTO(retail.isFragile(), retail.getSize(),
                retail.getWeight(), retail.getFromAddress().toDto(), retail.getToAddress().toDto());

        var orderData = ordersClient.createOrder(orderCreateRequest, userId.toString());

        var paymentToken = paymentService.sendPaymentRequest(retail.getPrice(), retail.getCurrency(), retailId);

        retail.setPayment(new Payment(paymentToken, null));
        retail.setOrderId(orderData.getId());

        retailRepository.save(retail);

        return orderData;
    }

    @Transactional
    public void confirmPayment(PaymentDTO paymentDTO, UUID retailId) {
        var retail = retailRepository.findById(retailId)
                .orElseThrow(RetailNotFound::new);

        if (retail.getPayment().getPaymentToken().equals(paymentDTO.getPaymentToken())) {
            throw new InvalidPaymentTokenException();
        }

        var orderStatusUpdateRequest = new OrderStateUpdateDTO("Payment received", OrderState.AWAITING_COLLECTION, retailId);

        ordersClient.updateStatus(retail.getOrderId(), orderStatusUpdateRequest);

        retail.getPayment().setPaymentMethod(paymentDTO.getPaymentMethod());

        retailRepository.save(retail);
    }
}
