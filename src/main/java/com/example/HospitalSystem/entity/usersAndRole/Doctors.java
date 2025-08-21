package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.constant.EmployeeStatus;
import com.example.HospitalSystem.entity.appointments.Medical_records;
import com.example.HospitalSystem.entity.appointments.Schedules;
import com.example.HospitalSystem.entity.appointments.Appointments;
import com.example.HospitalSystem.entity.appointments.Specialties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctors {
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
    private Specialties specialty;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @OneToMany(mappedBy = "doctor")
    private List<Medical_records> medicalRecordsList;

    @OneToMany(mappedBy = "doctor")
    private List<Schedules> schedulesList;

    @OneToMany(mappedBy = "doctor")
    private List<Appointments> appointmentsList;
}
