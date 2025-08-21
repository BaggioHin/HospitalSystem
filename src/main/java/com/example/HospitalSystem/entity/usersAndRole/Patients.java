package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.entity.appointments.Appointments;
import com.example.HospitalSystem.entity.appointments.Medical_records;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String insuranceNumber;
    private String emergencyContact;
    private String profileImageUrl;
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @OneToMany(mappedBy = "patient")
    private List<Appointments> appointmentsList;

    @OneToMany(mappedBy = "patient")
    private List<Medical_records> medicalRecordsList;

    @OneToMany(mappedBy = "patient")
    private List<Invoices> invoicesList;

}
