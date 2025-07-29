package com.example.HospitalSystem.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfResponse {
    private String username;
    private String profileImageUrl;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;
    private String insuranceNumber;
    private String emergencyContact;
    private Boolean isActive;
    private String gender;
}
