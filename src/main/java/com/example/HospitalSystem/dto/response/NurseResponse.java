package com.example.HospitalSystem.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseResponse {
    private Long id;
    private String username;
    private String workingShift;
    private String status;
}
