package com.example.HospitalSystem.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailResponse {
    private Long id;
    private String name;
    private String degree;
    private Integer experienceYears;
    private String profileImageUrl;
    private String status;
    private String licenseNumber;
}
