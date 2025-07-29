package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.entity.usersAndRole.doctors;
import com.example.HospitalSystem.entity.usersAndRole.patients;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class medical_records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnosis;
    private String symtoms;
    private String clinical_notes;
    private LocalDate created_at;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private patients patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private doctors doctor;

    @OneToMany(mappedBy = "medicalRecord")
    private List<prescriptions> prescriptionList;
}
