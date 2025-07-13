package com.example.HospitalSystem.entity.usersAndRole;

import jakarta.persistence.*;
import lombok.*;

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
}
