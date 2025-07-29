package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.entity.appointments.appointments;
import com.example.HospitalSystem.entity.appointments.medical_records;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String insuranceNumber;
    private String emergencyContact;
    private String profileImageUrl;
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private users user;

    @OneToMany(mappedBy = "patient")
    private List<appointments> appointmentsList;

    @OneToMany(mappedBy = "patient")
    private List<medical_records> medicalRecordsList;
}
