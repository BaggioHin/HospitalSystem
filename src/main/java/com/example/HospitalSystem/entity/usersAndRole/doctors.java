package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.constant.EmployeeStatus;
import com.example.HospitalSystem.entity.appointments.medical_records;
import com.example.HospitalSystem.entity.appointments.schedules;
import com.example.HospitalSystem.entity.appointments.appointments;
import com.example.HospitalSystem.entity.appointments.specialties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String degree;
    private Integer experienceYears;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    private String licenseNumber;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private specialties specialty;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private users user;

    @OneToMany(mappedBy = "doctor")
    private List<medical_records> medicalRecordsList;

    @OneToMany(mappedBy = "doctor")
    private List<schedules> schedulesList;

    @OneToMany(mappedBy = "doctor")
    private List<appointments> appointmentsList;
}
