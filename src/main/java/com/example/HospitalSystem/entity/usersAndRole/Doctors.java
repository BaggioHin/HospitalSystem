package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.entity.appointments.Schedules;
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
    private String status;
    private String licenseNumber;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @OneToMany(mappedBy = "doctor")
    private List<Schedules> schedulesList;
}
