package com.lukasikm.delivery.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Credentials not valid")
public class CredentialsIncorrectException extends RuntimeException {
}
