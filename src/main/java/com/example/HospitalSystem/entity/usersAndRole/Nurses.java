package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.constant.EmployeeStatus;
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
public class Nurses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToOne
    @JoinColumn(name = "ueser_id",nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialties specialty;

    @OneToMany(mappedBy = "nurse")
    private List<Schedules> schedulesList;

    @OneToMany(mappedBy = "nurse")
    private List<Appointments> appointmentsList;
}

