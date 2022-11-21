package com.example.demo.converter;

import com.example.demo.openapi.model.Invoice;
import com.example.demo.persistence.entity.InvoiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
public class InvoiceEntityToInvoiceConverter implements Converter<InvoiceEntity, Invoice> {

    private final InvoiceItemEntityToInvoiceItemConverter itemConverter;

    private final InvoiceStatusEntityToStatusConverter statusConverter;

    @Autowired
    public InvoiceEntityToInvoiceConverter(InvoiceItemEntityToInvoiceItemConverter itemConverter,
                                           InvoiceStatusEntityToStatusConverter statusConverter) {
        this.itemConverter = itemConverter;
        this.statusConverter = statusConverter;
    }

    @Override
    public Invoice convert(InvoiceEntity invoiceEntity) {
        var invoice = new Invoice();
        invoice.setId(invoiceEntity.getId());
        invoice.setCustomerEmail(invoiceEntity.getCustomerEmail());
        invoice.setCustomerName(invoiceEntity.getCustomerName());
        invoice.setDescription(invoiceEntity.getDescription());
        invoice.setDueDate(LocalDate.ofInstant(invoiceEntity.getDueDate(), ZoneOffset.UTC));
        invoice.setStatus(statusConverter.convert(invoiceEntity.getStatus()));
        invoice.setTotalAmount(invoiceEntity.getTotalAmount());

        var items = invoiceEntity.getInvoiceItems()
                .stream()
                .map(itemConverter::convert)
                .collect(Collectors.toList());
        invoice.setInvoiceItems(items);
        return invoice;
    }
}
