package com.example.demo.converter;

import com.example.demo.openapi.model.InvoiceItem;
import com.example.demo.persistence.entity.InvoiceItemEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceItemToInvoiceEntityConverter implements Converter<InvoiceItem, InvoiceItemEntity> {

    @Override
    public InvoiceItemEntity convert(InvoiceItem invoiceItem) {
        var itemEntity = new InvoiceItemEntity();
        itemEntity.setDescription(invoiceItem.getDescription());
        itemEntity.setAmount(invoiceItem.getAmount());
        return itemEntity;
    }
}
