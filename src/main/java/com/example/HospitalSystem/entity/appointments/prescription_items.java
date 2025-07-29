package com.example.HospitalSystem.entity.appointments;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class prescription_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medicine_name;
    private String dosage;
    private String duration;
    private String note;

    @ManyToOne
    @JoinColumn(name = "prescriptions")
    private prescriptions prescriptions;
}
