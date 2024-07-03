package com.uj.billswift.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.uj.billswift.clients.Clients;
import com.uj.billswift.clients.ClientsRepository;
import com.uj.billswift.company.Company;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientsRepository clientsRepository;
    
    @PostMapping("/issue")
    public ResponseEntity<String> issueInvoice(@RequestBody Invoice invoice) throws IOException { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário está autenticado e se é do tipo Company
        if (authentication == null || !(authentication.getPrincipal() instanceof Company)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        Company authenticatedCompany = (Company) authentication.getPrincipal();
        invoice.setEmitter(authenticatedCompany);

        invoice.setInvoiceNumber(invoice.getInvoiceNumber());
        invoice.setTransactionNature(invoice.getTransactionNature());
        invoice.setIssueDate(invoice.getIssueDate());
        invoice.setEntryExitDate(invoice.getEntryExitDate());
        invoice.setSeries(invoice.getSeries());
        invoice.setPurpose(invoice.getPurpose());
        invoice.setDocumentType(invoice.getDocumentType());

        Clients recipient = clientsRepository.findByName(invoice.getRecipient().getName());
        if (recipient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destinatário não encontrado.");
        }

        invoice.setRecipient(recipient);


        try {
            invoiceService.issueInvoice(invoice);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar PDF da nota fiscal");
        }
        
        return ResponseEntity.ok(invoice.getPdfPath());
    }

    @GetMapping("/list{emitterId}")
    public ResponseEntity<List<Invoice>> listInvoices(@RequestParam String emitterId) {
        List<Invoice> invoices = invoiceService.listInvoices(emitterId);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping("/cancel/{number}")
    public ResponseEntity<Void> cancelInvoice(@PathVariable int number) {
        invoiceService.cancelInvoice(number);
        return ResponseEntity.noContent().build();
    }
}
