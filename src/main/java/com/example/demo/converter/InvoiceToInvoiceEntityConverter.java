package com.example.demo.converter;

import com.example.demo.openapi.model.Invoice;
import com.example.demo.persistence.entity.InvoiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class InvoiceToInvoiceEntityConverter implements Converter<Invoice, InvoiceEntity> {

    private final StatusToInvoiceStatusEntityConverter statusConverter;

    @Autowired
    public InvoiceToInvoiceEntityConverter(StatusToInvoiceStatusEntityConverter statusConverter) {
        this.statusConverter = statusConverter;
    }

    @Override
    public InvoiceEntity convert(Invoice invoice) {
        var invoiceEntity = new InvoiceEntity();
        invoiceEntity.setCustomerEmail(invoice.getCustomerEmail());
        invoiceEntity.setCustomerName(invoice.getCustomerName());
        invoiceEntity.setDescription(invoice.getDescription());
        invoiceEntity.setTotalAmount(invoice.getTotalAmount());
        invoiceEntity.setDueDate(invoice.getDueDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        invoiceEntity.setStatus(statusConverter.convert(invoice.getStatus()));
        return invoiceEntity;
    }
}
