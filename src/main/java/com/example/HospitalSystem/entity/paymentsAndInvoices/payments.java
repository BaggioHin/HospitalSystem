package com.example.HospitalSystem.entity.paymentsAndInvoices;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Enum payment_method;
    private BigDecimal amount;
    private LocalDate paid_at;
    private String transaction_code;

    @ManyToOne
    @JoinColumn(name = "invoices_id")
    private invoices invoice;
}
