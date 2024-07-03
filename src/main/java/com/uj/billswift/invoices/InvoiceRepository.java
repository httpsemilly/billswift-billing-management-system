package com.uj.billswift.invoices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String>{
    List<Invoice> findByEmitterId(String emitterId);
    Invoice findByInvoiceNumber(int invoiceNumber);
}
