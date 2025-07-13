package com.example.HospitalSystem.entity.statistics;

import com.example.HospitalSystem.entity.usersAndRole.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class audit_logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String entity_type;
    private String entity_id;
    private LocalDate timestamp;
    private String ip_address;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
