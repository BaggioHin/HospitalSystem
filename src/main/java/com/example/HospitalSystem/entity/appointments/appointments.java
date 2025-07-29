package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.constant.AppointmentStatus;
import com.example.HospitalSystem.constant.BookingType;
import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.entity.paymentsAndInvoices.deposits;
import com.example.HospitalSystem.entity.paymentsAndInvoices.invoices;
import com.example.HospitalSystem.entity.services.serviceResults;
import com.example.HospitalSystem.entity.usersAndRole.doctors;
import com.example.HospitalSystem.entity.usersAndRole.patients;
import com.example.HospitalSystem.entity.usersAndRole.receptionists;
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
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private LocalDate appointmentDate;
    private LocalDate appointmentTime;
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;
    private String notes;
    @Enumerated(EnumType.STRING)
    private PaymentStatus payment_status;

    @ManyToOne
    @JoinColumn(name = "receptionist_id")
    private receptionists receptionist;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private patients patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private doctors doctor;

    @OneToMany(mappedBy = "appointment")
    private List<serviceResults> serviceResults;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private invoices invoice;

    @OneToMany(mappedBy = "appointment")
    private List<deposits> deposits;
}
