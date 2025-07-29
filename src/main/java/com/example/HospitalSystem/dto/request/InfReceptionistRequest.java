package com.example.HospitalSystem.dto.request;

import com.example.HospitalSystem.constant.EmployeeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfReceptionistRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;
    private String gender;
    private String specialty;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
}
