package com.uj.billswift.invoices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice issueInvoice(Invoice invoice) throws IOException {
        List<Invoice> invoices = invoiceRepository.findAll();
        invoice.setInvoiceNumber(invoices.size() + 1);

        String pdfPath = generateUniquePdfPath(invoice);
        invoice.setPdfPath(pdfPath);

        PdfService.generatePdf(invoice);

        invoiceRepository.save(invoice);

        return invoice;
    }

    public List<Invoice> listInvoices(String emitterId) {
        return invoiceRepository.findByEmitterId(emitterId);
    }

    public void cancelInvoice(int number) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(number);
        if(invoice != null) {
            invoiceRepository.delete(invoice);
        }
    }

    private String generateUniquePdfPath(Invoice invoice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "pdfs/invoice_" + invoice.getInvoiceNumber() + "_" + timestamp + ".pdf";
    }
}