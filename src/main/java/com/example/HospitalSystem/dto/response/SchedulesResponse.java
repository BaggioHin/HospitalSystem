package com.example.HospitalSystem.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchedulesResponse {
    private LocalDate workingDate;
    private LocalTime startTime;
    private String endTime;
    private String location;
    private String speciality;
    private Integer maxPatients;
    private Integer currentPatients;
    private String status;
}
