package com.example.HospitalSystem.entity.usersAndRole;

import com.example.HospitalSystem.constant.EmployeeStatus;
import com.example.HospitalSystem.entity.appointments.schedules;
import com.example.HospitalSystem.entity.appointments.appointments;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class receptionists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToOne
    @JoinColumn(name = "ueser_id",nullable = false)
    private users user;

    @OneToMany(mappedBy = "receptionist")
    private List<schedules> schedulesList;

    @OneToMany(mappedBy = "receptionist")
    private List<appointments> appointmentsList; ;
}
