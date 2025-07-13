package com.example.HospitalSystem.entity.usersAndRole;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Receptionists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workingShift;
    private String status;

    @OneToOne
    @JoinColumn(name = "ueser_id",nullable = false)
    private Users user;
}
