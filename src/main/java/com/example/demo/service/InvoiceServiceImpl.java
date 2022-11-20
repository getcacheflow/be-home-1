package com.example.demo.service;

import com.example.demo.openapi.model.Invoice;
import com.example.demo.openapi.model.InvoiceItem;
import com.example.demo.openapi.model.Status;
import com.example.demo.persistence.entity.InvoiceEntity;
import com.example.demo.persistence.repository.InvoiceRepository;
import com.example.demo.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final ValidationUtils validationUtils;

    private final ConversionService conversionService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              ValidationUtils validationUtils,
                              ConversionService conversionService) {
        this.invoiceRepository = invoiceRepository;
        this.validationUtils = validationUtils;
        this.conversionService = conversionService;
    }

    @Override
    public UUID createInvoice(Invoice invoice) {
        validationUtils.validateInvoice(invoice);
        var invoiceEntity = conversionService.convert(invoice, InvoiceEntity.class);
        var savedInvoice = invoiceRepository.save(invoiceEntity);

        return savedInvoice.getId();
    }

    @Override
    public List<Invoice> getAllInvoicesByStatus(Status status) {
        return null;
    }

    @Override
    public void addInvoiceItemToInvoice(UUID invoiceId, List<InvoiceItem> items) {

    }

    @Override
    public Invoice updateInvoice(UUID invoiceId, Invoice updatedInvoice) {
        return null;
    }

    @Override
    public Invoice patchInvoice(UUID invoiceId) {
        return null;
    }
}
