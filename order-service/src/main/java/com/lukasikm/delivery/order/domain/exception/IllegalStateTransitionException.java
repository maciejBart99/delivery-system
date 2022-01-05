package com.lukasikm.delivery.order.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Illegal order state change request")
public class IllegalStateTransitionException extends RuntimeException {
}
