package com.example.demo.exception;

public abstract class InvoiceBaseException extends RuntimeException {
    private final String description;

    public InvoiceBaseException(String description) {
        super(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
