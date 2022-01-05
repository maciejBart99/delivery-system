package com.lukasikm.delivery.retail.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Retails not found")
public class RetailNotFound extends RuntimeException {
}
