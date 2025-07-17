package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import com.example.HospitalSystem.entity.usersAndRole.Nurses;
import com.example.HospitalSystem.entity.usersAndRole.Receptionists;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate workingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String speciality;
    private String location;
    private Integer maxPatients;
    private Integer currentPatients;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctors doctor;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurses nurse;

    @ManyToOne
    @JoinColumn(name = "receptionist_id")
    private Receptionists receptionist;
}
