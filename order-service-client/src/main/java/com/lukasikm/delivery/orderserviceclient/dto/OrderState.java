package com.lukasikm.delivery.orderserviceclient.dto;

public enum OrderState {
    AWAITING_PAYMENT, AWAITING_COLLECTION, ON_WAY_TO_WAREHOUSE, WAREHOUSE, ON_WAY_TO_DESTINATION, DELIVERED
}
