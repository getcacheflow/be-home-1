package com.example.demo.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An invoice contains the information required to identify the customer who is being billed, 
 * status, due date, amount and breakdown of each thing being invoiced
 */
@Entity
@Table(name = "invoice")
public class InvoiceEntity extends BaseEntity {

    public InvoiceEntity() {}

    @NotNull
    @Column(name = "customer_email")
    private String customerEmail;

    @NotNull
    @Column(name = "customer_name")
    private String customerName;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "due_date")
    private Instant dueDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private InvoiceStatusEntity status;

    @NotNull
    @Min(0)
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToMany(
            mappedBy = "invoice",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<InvoiceItemEntity> invoiceItems = new ArrayList<>();

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public InvoiceStatusEntity getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatusEntity status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<InvoiceItemEntity> getInvoiceItems() {
        return invoiceItems;
    }

    public void addInvoiceItem(InvoiceItemEntity invoiceItemEntity) {
        invoiceItems.add(invoiceItemEntity);
        invoiceItemEntity.setInvoice(this);
    }

    public void removePhoto(InvoiceItemEntity invoiceItemEntity) {
        invoiceItems.remove(invoiceItemEntity);
        invoiceItemEntity.setInvoice(null);
    }

    public void removeAllInvoiceItems() {
        invoiceItems.forEach(invoiceItemEntity -> invoiceItemEntity.setInvoice(null));
        invoiceItems.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntity that = (InvoiceEntity) o;
        return customerEmail.equals(that.customerEmail) && customerName.equals(that.customerName) && description.equals(that.description) && dueDate.equals(that.dueDate) && status == that.status && totalAmount.equals(that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerEmail, customerName, description, dueDate, status, totalAmount);
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "customerEmail='" + customerEmail + '\'' +
                ", customerName='" + customerName + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
