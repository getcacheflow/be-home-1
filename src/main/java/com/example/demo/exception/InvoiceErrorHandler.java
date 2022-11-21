package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class InvoiceErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        var invoiceApiError = InvoiceApiError.from(NOT_FOUND, ex.getMessage());
        return buildResponseEntity(invoiceApiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        var invoiceApiError = InvoiceApiError.from(BAD_REQUEST, ex.getMessage());
        return buildResponseEntity(invoiceApiError);
    }


    private ResponseEntity<Object> buildResponseEntity(InvoiceApiError invoiceApiError) {
        return new ResponseEntity<>(invoiceApiError, invoiceApiError.getStatus());
    }
}
