package com.lukasikm.delivery.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Token missing or not valid")
public class TokenException extends RuntimeException {
}
