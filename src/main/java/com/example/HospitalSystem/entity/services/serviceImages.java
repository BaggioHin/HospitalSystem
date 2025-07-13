package com.example.HospitalSystem.entity.services;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class serviceImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image_url;
    private String description;
    private Date upload_at;

    @ManyToOne
    @JoinColumn(name = "serviceResult_id")
    private serviceResults serviceResult;
}
