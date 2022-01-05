package com.lukasikm.delivery.order.domain;

import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;

public interface TagGenerator {
    byte[] generateTag(OrderDTO order);
}
