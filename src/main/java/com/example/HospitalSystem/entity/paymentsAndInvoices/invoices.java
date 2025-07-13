package com.example.HospitalSystem.entity.paymentsAndInvoices;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal total_amount;
    private Enum status;
    private Date created_at;
    private LocalDate paid_at;

    @OneToMany(mappedBy = "invoice")
    private List<payments> payments;

    @OneToMany(mappedBy = "invoice")
    private List<invoiceItems> invoiceItems;
}
