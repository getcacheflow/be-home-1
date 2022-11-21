package com.example.demo.converter;

import com.example.demo.openapi.model.Status;
import com.example.demo.persistence.entity.InvoiceStatusEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceStatusEntityToStatusConverter implements Converter<InvoiceStatusEntity, Status> {

    @Override
    public Status convert(InvoiceStatusEntity invoiceStatusEntity) {
        return Status.valueOf(invoiceStatusEntity.name());
    }
}
