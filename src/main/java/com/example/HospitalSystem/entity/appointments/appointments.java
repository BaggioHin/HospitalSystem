package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.entity.paymentsAndInvoices.deposits;
import com.example.HospitalSystem.entity.paymentsAndInvoices.invoices;
import com.example.HospitalSystem.entity.services.serviceResults;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private LocalDate appointmentTime;
    private String bookingType;
    private String notes;
    private String payment_status;

    @OneToMany(mappedBy = "appointment")
    private List<serviceResults> serviceResults;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private invoices invoice;

    @OneToMany(mappedBy = "appointment")
    private List<deposits> deposits;
}
