package com.example.demo.utils;

import com.example.demo.exception.IllegalArgumentException;
import com.example.demo.openapi.model.Invoice;
import com.example.demo.openapi.model.Status;
import com.example.demo.persistence.entity.InvoiceEntity;
import com.example.demo.persistence.entity.InvoiceStatusEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidationUtils {

    public void validateInvoice(Invoice invoice) {

    }

    public void validateUpdateInvoice(InvoiceEntity existingInvoice, Invoice updatedInvoice) {
        validateStatusForUpdate(existingInvoice.getStatus());
        validateTotalAmountForUpdate(updatedInvoice.getTotalAmount(), existingInvoice);
    }

    public void validateStatusForUpdate(InvoiceStatusEntity status) {
        if (InvoiceStatusEntity.DRAFT != status) {
            throw IllegalArgumentException.from("Only invoice in DRAFT status can be updated");
        }
    }

    public void validateTotalAmountForUpdate(BigDecimal totalAmount, InvoiceEntity invoice) {
        var totalAmountPerInvoice = invoice.getInvoiceItems()
                .stream()
                .map(item -> item.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!invoice.getInvoiceItems().isEmpty() && totalAmount.compareTo(totalAmountPerInvoice) != 0) {
            throw IllegalArgumentException.from("Invoice total amount is different from total amount of invoice items");
        }
    }
}
