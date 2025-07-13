package com.example.HospitalSystem.entity.paymentsAndInvoices;

import com.example.HospitalSystem.entity.services.services;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class invoiceItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private BigDecimal unit_price;
    private BigDecimal total_price;

    @ManyToOne
    @JoinColumn(name = "invoice")
    private invoices invoice;

    @OneToOne
    @JoinColumn(name = "services_id")
    private services service;
}
