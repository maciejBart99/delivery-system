package com.lukasikm.delivery.authserviceclient;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AuthClientExceptionHandler {
    @ExceptionHandler(FeignException.Unauthorized.class)
    public ResponseEntity<String> resourceNotFoundException(FeignException exception) {
        return new ResponseEntity<String>("Unauthorized", HttpStatus.FORBIDDEN);
    }
}