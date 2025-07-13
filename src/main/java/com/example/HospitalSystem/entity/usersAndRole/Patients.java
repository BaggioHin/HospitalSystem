package com.example.HospitalSystem.entity.usersAndRole;

import jakarta.persistence.*;
import lombok.*;

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
    private String citizenId;
    private String insuranceNumber;
    private String emergencyContact;
    private String healthHistory;
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "ueser_id",nullable = false)
    private Users user;
}
