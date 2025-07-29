package com.example.HospitalSystem.dto.request;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfPatientRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;
    private String gender;
    private String insuranceNumber;
    private String emergencyContact;
    private String profileImageUrl;
    private Boolean isActive;
}
