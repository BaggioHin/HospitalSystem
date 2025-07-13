package com.example.HospitalSystem.entity.paymentsAndInvoices;

import com.example.HospitalSystem.entity.appointments.appointments;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class deposits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Enum status;
    private Date created_at;
    private Date paid_at;

    @ManyToOne
    @JoinColumn(name = "appointments_id")
    private appointments appointment;
}
