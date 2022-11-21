package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.openapi.model.Invoice;
import com.example.demo.openapi.model.InvoiceItem;
import com.example.demo.openapi.model.Status;
import com.example.demo.persistence.entity.InvoiceEntity;
import com.example.demo.persistence.entity.InvoiceItemEntity;
import com.example.demo.persistence.entity.InvoiceStatusEntity;
import com.example.demo.persistence.repository.InvoiceRepository;
import com.example.demo.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Invoice createInvoice(Invoice invoice) {
        validationUtils.validateInvoice(invoice);
        var invoiceEntity = conversionService.convert(invoice, InvoiceEntity.class);
        var savedInvoice = invoiceRepository.save(invoiceEntity);

        return conversionService.convert(savedInvoice, Invoice.class);
    }

    @Override
    public List<Invoice> getAllInvoicesByStatus(Status status) {
        var invoices = invoiceRepository.findAllByStatus(conversionService.convert(status, InvoiceStatusEntity.class));
        return invoices.stream()
                .map(invoiceEntity -> conversionService.convert(invoiceEntity, Invoice.class))
                .collect(Collectors.toList());
    }

    @Override
    public Invoice addInvoiceItemsToInvoice(UUID invoiceId, List<InvoiceItem> items) {
        var existingInvoiceEntity = getInvoice(invoiceId);
        var totalAmountExistingItems = existingInvoiceEntity.getInvoiceItems().stream()
                .map(InvoiceItemEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        items.stream()
                .map(invoiceItem -> conversionService.convert(invoiceItem, InvoiceItemEntity.class))
                .forEachOrdered(existingInvoiceEntity::addInvoiceItem);
        var totalAmount = items.stream()
                .map(InvoiceItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        existingInvoiceEntity.setTotalAmount(totalAmount.add(totalAmountExistingItems));

        var invoiceWithItems = invoiceRepository.save(existingInvoiceEntity);
        return conversionService.convert(invoiceWithItems, Invoice.class);
    }

    @Override
    public Invoice updateInvoice(UUID invoiceId, Invoice updatedInvoice) {
        var existingInvoiceEntity = getInvoice(invoiceId);

        validationUtils.validateUpdateInvoice(existingInvoiceEntity, updatedInvoice);
        existingInvoiceEntity.setCustomerEmail(updatedInvoice.getCustomerEmail());
        existingInvoiceEntity.setDescription(updatedInvoice.getDescription());
        existingInvoiceEntity.setCustomerName(updatedInvoice.getCustomerName());
        existingInvoiceEntity.setStatus(conversionService.convert(updatedInvoice.getStatus(), InvoiceStatusEntity.class));
        existingInvoiceEntity.setDueDate(updatedInvoice.getDueDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        existingInvoiceEntity.setTotalAmount(updatedInvoice.getTotalAmount());
        var updateInvoiceEntity = invoiceRepository.save(existingInvoiceEntity);

        return conversionService.convert(updateInvoiceEntity, Invoice.class);
    }

    @Override
    public Invoice updateInvoiceStatus(UUID invoiceId, Status status) {
        //TODO Define service responsible for validation of the changes to status (state machine)
        var existingInvoiceEntity = getInvoice(invoiceId);
        existingInvoiceEntity.setStatus(conversionService.convert(status, InvoiceStatusEntity.class));
        var invoiceWithUpdatedStatus = invoiceRepository.save(existingInvoiceEntity);
        return conversionService.convert(invoiceWithUpdatedStatus, Invoice.class);
    }

    private InvoiceEntity getInvoice(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> {
                    logger.info("Could not retrieve an invoice with  id={}", invoiceId);
                    return ResourceNotFoundException.from("Invoice is not found");
                });
    }
}
