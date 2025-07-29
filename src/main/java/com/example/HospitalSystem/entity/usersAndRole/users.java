package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.entity.statistics.audit_logs;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;
    private String gender;

    @OneToOne(mappedBy = "user")
    private doctors doctor;

    @OneToOne(mappedBy = "user")
    private nurses nurse;

    @OneToOne(mappedBy = "user")
    private patients patient;

    @OneToOne(mappedBy = "user")
    private receptionists receptionist;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<com.example.HospitalSystem.entity.usersAndRole.roles> roles;

    @OneToMany(mappedBy = "user")
    private List<audit_logs> auditLog;

    @OneToMany(mappedBy = "user")
    private Set<com.example.HospitalSystem.entity.usersAndRole.emailEncoder> emailEncoder;
}
