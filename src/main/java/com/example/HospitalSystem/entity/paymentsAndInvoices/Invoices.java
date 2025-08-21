package com.example.HospitalSystem.entity.paymentsAndInvoices;

import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.entity.usersAndRole.Patients;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long total_amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus PaymentStatus;
    private Date created_at;
    private LocalDate paid_at;
//    private boolean modify;
//    private String description;


    @OneToMany(mappedBy = "invoice")
    private List<Payments> payments;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItems> invoiceItems;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;
}
