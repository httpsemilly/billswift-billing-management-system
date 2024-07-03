package com.uj.billswift.invoices;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.uj.billswift.company.Company;
import com.uj.billswift.clients.Clients;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String transactionNature;
    private LocalDate issueDate;
    private LocalDate entryExitDate;
    private int series;
    private int invoiceNumber;
    private int purpose;
    private int documentType;
    private String productDescription;
    private String productCode;
    private float price;
    private float shipping;
    private float discount;
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emitter_id")
    private Company emitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Clients recipient;

    private String pdfPath;
    private LocalDateTime createdAt;
}
