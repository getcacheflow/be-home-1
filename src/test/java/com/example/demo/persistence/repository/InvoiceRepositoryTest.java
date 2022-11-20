package com.example.demo.persistence.repository;


import com.example.demo.persistence.entity.InvoiceEntity;
import com.example.demo.persistence.entity.InvoiceItemEntity;
import com.example.demo.persistence.entity.InvoiceStatusEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@EnableJpaAuditing
class InvoiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void findAllInvoicesByStatus() {

        var invoice1  = generateTestInvoice("favorite_customer@curious.com", "Amazing Customer", InvoiceStatusEntity.DRAFT);
        var invoice2  = generateTestInvoice("favorite_customer2@curious.com","Another Amazing Customer", InvoiceStatusEntity.APPROVED);
        var invoice3  = generateTestInvoice("favorite_customer3@curious.com", "One More Amazing Customer", InvoiceStatusEntity.DRAFT);

        var invoice1Item1 = new InvoiceItemEntity();
        invoice1Item1.setAmount(BigDecimal.valueOf(21.34));
        invoice1Item1.setDescription("Just something to be billed for");
        invoice1.addInvoiceItem(invoice1Item1);

        entityManager.persistAndFlush(invoice1);
        entityManager.persistAndFlush(invoice2);
        entityManager.persistAndFlush(invoice3);

        var allInvoicesInDraft = invoiceRepository.findAllByStatus(InvoiceStatusEntity.DRAFT);
        assertThat(allInvoicesInDraft).isNotEmpty();
        assertThat(allInvoicesInDraft.size()).isEqualTo(2);

        var allInvoicesInDraftVersion2 = invoiceRepository.findAllByStatusWithItems(InvoiceStatusEntity.DRAFT);
        assertThat(allInvoicesInDraftVersion2.size()).isEqualTo(2);
    }

    private InvoiceEntity generateTestInvoice(String customerEmail, String customerName, InvoiceStatusEntity status) {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCustomerEmail(customerEmail);
        invoice.setCustomerName(customerName);
        invoice.setStatus(status);
        invoice.setDescription("You just need to pay this amount and everything will be great");
        invoice.setDueDate(Instant.now().plus(3, ChronoUnit.DAYS));
        invoice.setTotalAmount(BigDecimal.valueOf(215.47));
        return invoice;
    }

}