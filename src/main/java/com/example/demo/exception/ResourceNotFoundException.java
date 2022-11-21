package com.example.demo.exception;

public class ResourceNotFoundException extends InvoiceBaseException {

    public ResourceNotFoundException(String description) {
        super(description);
    }

    public static ResourceNotFoundException from(String description) {
        return new ResourceNotFoundException(description);
    }
}
