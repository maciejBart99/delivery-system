package com.lukasikm.delivery.retail.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Cannot reconfirm this retail process")
public class RetailAlreadyConfirmed extends RuntimeException {
}
