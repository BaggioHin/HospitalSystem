package com.example.HospitalSystem.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
}
