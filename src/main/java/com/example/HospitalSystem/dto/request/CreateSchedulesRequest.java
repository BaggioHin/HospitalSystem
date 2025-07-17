package com.example.HospitalSystem.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchedulesRequest {
    private Long id;
    private LocalDate workingDate;
    private LocalTime startTime;
    private String endTime;
    private String location;
    private String speciality;
    private Integer maxPatients;
    private Integer currentPatients;
    private String status;
}
