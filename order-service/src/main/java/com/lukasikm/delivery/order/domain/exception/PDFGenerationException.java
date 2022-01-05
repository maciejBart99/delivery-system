package com.lukasikm.delivery.order.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Pdf generation error")
public class PDFGenerationException extends RuntimeException {
}
