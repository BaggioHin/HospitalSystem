package com.example.HospitalSystem.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectRequest {
    private String token;
}
