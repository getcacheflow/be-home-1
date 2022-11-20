package com.example.demo;

import com.example.demo.openapi.api.InvoicesApi;
import com.example.demo.openapi.model.CreateInvoiceResponse;
import com.example.demo.openapi.model.GetInvoicesResponse;
import com.example.demo.openapi.model.Invoice;
import com.example.demo.openapi.model.Status;
import com.example.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController implements InvoicesApi {

  private final InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/")
  public String index() {
    return "Greetings from Cacheflow!";
  }

  @Override
  public ResponseEntity<GetInvoicesResponse> _getInvoices(Status status) {
    return InvoicesApi.super.getInvoices(status);
  }

  @Override
  public ResponseEntity<CreateInvoiceResponse> _createInvoice(Invoice invoice) {
    var invoiceId = invoiceService.createInvoice(invoice);
    var response = new CreateInvoiceResponse();
    response.setId(invoiceId);
    return ResponseEntity.ok(response);
  }
}