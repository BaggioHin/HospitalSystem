package com.example.HospitalSystem.dto.request;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignIn {
    private String username;
    private String password;
}
