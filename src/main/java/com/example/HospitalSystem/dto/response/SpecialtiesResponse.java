package com.example.HospitalSystem.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtiesResponse {
    private String name;
    private String description;
    private Boolean active;
    private String image_url;
}

