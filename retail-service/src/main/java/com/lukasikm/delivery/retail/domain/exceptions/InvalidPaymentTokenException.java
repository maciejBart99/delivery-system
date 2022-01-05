package com.lukasikm.delivery.retail.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Payment token is invalid")
public class InvalidPaymentTokenException extends RuntimeException {
}
