package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.InvoiceEntity;
import com.example.demo.persistence.entity.InvoiceStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {

    List<InvoiceEntity> findAllByStatus(InvoiceStatusEntity status);

    @Query("SELECT invoice from InvoiceEntity invoice" +
            " LEFT JOIN FETCH invoice.invoiceItems invoiceItems"
            + " WHERE invoice.status = :status")
    List<InvoiceEntity> findAllByStatusWithItems(InvoiceStatusEntity status);


}
