package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.entity.chat.RoomMember;
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
public class Users {
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
    private Doctors doctor;

    @OneToOne(mappedBy = "user")
    private Nurses nurse;

    @OneToOne(mappedBy = "user")
    private Patients patient;

    @OneToOne(mappedBy = "user")
    private Receptionists receptionist;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;

    @OneToMany(mappedBy = "user")
    private List<audit_logs> auditLog;

    @OneToMany(mappedBy = "user")
    private Set<EmailEncoder> emailEncoder;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomMember> roomMembers;

}
