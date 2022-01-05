package com.lukasikm.delivery.orderserviceclient;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderClientExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class OrderServiceError extends RuntimeException {
        OrderServiceError(String cause) {
            super(cause);
        }
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignBadRequestException(FeignException e) {
        throw new OrderServiceError(e.getMessage());
    }
}
