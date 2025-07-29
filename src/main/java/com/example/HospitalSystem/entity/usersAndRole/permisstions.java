package com.example.HospitalSystem.entity.usersAndRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class permisstions {
    @Id
    private String id;
    private String description;
}
