package com.example.HospitalSystem.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSpecialty {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private String image_url;
}
