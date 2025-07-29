package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.constant.ScheduleStatus;
import com.example.HospitalSystem.constant.SchedulesType;
import com.example.HospitalSystem.entity.usersAndRole.doctors;
import com.example.HospitalSystem.entity.usersAndRole.nurses;
import com.example.HospitalSystem.entity.usersAndRole.receptionists;
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
public class schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate workingDate;
    private LocalTime startTime;
    private LocalTime endTime;
//    private String speciality;
    private String location;
    private Integer maxPatients;
    private Integer currentPatients;
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;
    @Enumerated(EnumType.STRING)
    private SchedulesType scheduleType;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private specialties specialties;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private doctors doctor;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private nurses nurse;

    @ManyToOne
    @JoinColumn(name = "receptionist_id")
    private receptionists receptionist;
}
