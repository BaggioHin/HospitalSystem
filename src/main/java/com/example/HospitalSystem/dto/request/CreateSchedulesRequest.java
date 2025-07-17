package com.example.HospitalSystem.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchedules {
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String location;
    private String speciality;
    private String status;
    private String object;
}
