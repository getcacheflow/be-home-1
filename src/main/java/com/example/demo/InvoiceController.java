package com.example.demo;

import com.example.demo.openapi.api.InvoicesApi;
import com.example.demo.openapi.model.CreateInvoiceResponse;
import com.example.demo.openapi.model.GetInvoicesResponse;
import com.example.demo.openapi.model.Invoice;
import com.example.demo.openapi.model.InvoiceItem;
import com.example.demo.openapi.model.InvoiceItems;
import com.example.demo.openapi.model.Status;
import com.example.demo.openapi.model.UpdateInvoiceResponse;
import com.example.demo.openapi.model.UpdateInvoiceStatusResponse;
import com.example.demo.openapi.model.UpdateStatusBody;
import com.example.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
  public ResponseEntity<GetInvoicesResponse> _getInvoices(String status) {
    var invoicesByStatus = invoiceService.getAllInvoicesByStatus(Status.fromValue(status));
    var response = new GetInvoicesResponse();
    response.setItems(invoicesByStatus);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CreateInvoiceResponse> _createInvoice(Invoice invoice) {
    var createdInvoice = invoiceService.createInvoice(invoice);
    var response = new CreateInvoiceResponse();
    response.setInvoice(createdInvoice);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<UpdateInvoiceResponse> _updateInvoice(UUID id, Invoice invoice) {
    var updatedInvoice = invoiceService.updateInvoice(id, invoice);
    var response = new UpdateInvoiceResponse();
    response.setInvoice(updatedInvoice);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<UpdateInvoiceStatusResponse> _updateStatus(UUID id, UpdateStatusBody updateStatusBody) {
    var invoiceWithUpdatedStatus = invoiceService.updateInvoiceStatus(id, updateStatusBody.getStatus());
    var response = new UpdateInvoiceStatusResponse();
    response.setInvoice(invoiceWithUpdatedStatus);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<UpdateInvoiceResponse> _addItemsToInvoice(UUID id, InvoiceItems invoiceItems) {
    var updatedInvoice = invoiceService.addInvoiceItemsToInvoice(id, invoiceItems.getItems());
    var response = new UpdateInvoiceResponse();
    response.setInvoice(updatedInvoice);
    return ResponseEntity.ok(response);
  }
}