package com.lukasikm.delivery.warehouse.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Warehouse not found")
public class WarehouseNotFoundException extends RuntimeException {
}
