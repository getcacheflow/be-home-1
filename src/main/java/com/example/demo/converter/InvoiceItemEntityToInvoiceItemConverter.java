package com.example.demo.converter;

import com.example.demo.openapi.model.InvoiceItem;
import com.example.demo.persistence.entity.InvoiceItemEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceItemEntityToInvoiceItemConverter implements Converter<InvoiceItemEntity, InvoiceItem> {

    @Override
    public InvoiceItem convert(InvoiceItemEntity invoiceItemEntity) {
        var invoiceItem = new InvoiceItem();
        invoiceItem.setDescription(invoiceItemEntity.getDescription());
        invoiceItem.setAmount(invoiceItemEntity.getAmount());
        return invoiceItem;
    }
}
