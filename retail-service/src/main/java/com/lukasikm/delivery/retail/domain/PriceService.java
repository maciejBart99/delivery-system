package com.lukasikm.delivery.retail.domain;

import com.lukasikm.delivery.retailserviceclient.dto.RetailParametersDTO;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    public Price estimatePrice(RetailParametersDTO retailParameters) {
        // dumb logic
        var amount = retailParameters.getWeight() * (1 + retailParameters.getSize().ordinal());

        if (retailParameters.isFragile()) {
            amount *= 2;
        }

        return new Price(amount, "EUR");
    }
}
