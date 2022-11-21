package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private Map<String, String> errorDetails;

    public InvoiceApiError(HttpStatus status, String message, String debugMessage, Map<String, String> errorDetails) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.debugMessage = debugMessage;
        this.errorDetails = errorDetails;
    }

    public static InvoiceApiError from(HttpStatus status, String message) {
        return new InvoiceApiError(status, message, null, new HashMap<>());
    }

    public static InvoiceApiError from(HttpStatus status, Throwable ex) {
        return new InvoiceApiError(status, "Unexpected error", ex.getLocalizedMessage(), new HashMap<>());
    }

    public static InvoiceApiError from(HttpStatus status, String message, Throwable ex) {
        return new InvoiceApiError(status, message, ex.getLocalizedMessage(), new HashMap<>());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }
}
