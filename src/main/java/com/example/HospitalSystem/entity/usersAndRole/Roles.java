package com.example.HospitalSystem.entity.usersAndRole;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "role_permisstion",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permisstion_id")
    )
    private Set<Permisstions> permisstions;
}
