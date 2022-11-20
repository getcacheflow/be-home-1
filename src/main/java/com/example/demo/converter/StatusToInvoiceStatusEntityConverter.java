package com.example.demo.converter;

import com.example.demo.openapi.model.Status;
import com.example.demo.persistence.entity.InvoiceStatusEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusToInvoiceStatusEntityConverter implements Converter<Status, InvoiceStatusEntity> {
    @Override
    public InvoiceStatusEntity convert(Status status) {
        return InvoiceStatusEntity.valueOf(status.getValue().toUpperCase());
    }
}
