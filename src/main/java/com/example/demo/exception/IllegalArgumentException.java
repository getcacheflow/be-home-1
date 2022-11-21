package com.example.demo.exception;

public class IllegalArgumentException extends InvoiceBaseException{

    public IllegalArgumentException(String description) {
        super(description);
    }

    public static IllegalArgumentException from(String description) {
        return new IllegalArgumentException(description);
    }
}
