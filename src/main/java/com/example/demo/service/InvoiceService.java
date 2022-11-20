package com.example.demo.service;

import com.example.demo.openapi.model.Invoice;
import com.example.demo.openapi.model.InvoiceItem;
import com.example.demo.openapi.model.Status;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    UUID createInvoice(Invoice invoice);

    List<Invoice> getAllInvoicesByStatus(Status status);

    void addInvoiceItemToInvoice(UUID invoiceId, List<InvoiceItem> items);

    Invoice updateInvoice(UUID invoiceId, Invoice updatedInvoice);

    Invoice patchInvoice(UUID invoiceId);
}
