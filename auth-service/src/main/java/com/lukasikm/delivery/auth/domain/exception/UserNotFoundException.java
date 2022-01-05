package com.lukasikm.delivery.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot find user with this id")
public class UserNotFoundException extends RuntimeException {
}
