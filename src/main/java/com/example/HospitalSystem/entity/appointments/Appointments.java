package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.constant.AppointmentStatus;
import com.example.HospitalSystem.constant.BookingType;
import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Deposits;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import com.example.HospitalSystem.entity.services.serviceResults;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import com.example.HospitalSystem.entity.usersAndRole.Nurses;
import com.example.HospitalSystem.entity.usersAndRole.Patients;
import com.example.HospitalSystem.entity.usersAndRole.Receptionists;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDateTime;
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;
    private String notes;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "receptionist_id")
    private Receptionists receptionist;

    @ManyToOne
    @JoinColumn(name = "nurse")
    private Nurses nurse;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctors doctor;

    @OneToMany(mappedBy = "appointment")
    private List<serviceResults> serviceResults;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoices invoice;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Deposits deposits;
}
