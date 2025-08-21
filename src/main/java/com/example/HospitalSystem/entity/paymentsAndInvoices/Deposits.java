package com.example.HospitalSystem.entity.paymentsAndInvoices;

import com.example.HospitalSystem.entity.appointments.Appointments;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Deposits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created_at;
    private String transactionRef;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointments appointment;
}
