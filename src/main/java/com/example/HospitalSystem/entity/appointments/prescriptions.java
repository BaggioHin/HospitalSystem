package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.entity.usersAndRole.doctors;
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
public class prescriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate prescribed_date;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    private medical_records medicalRecord;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private doctors doctor;

    @OneToMany(mappedBy = "prescriptions")
    private List<prescription_items> prescriptionItems;
}
