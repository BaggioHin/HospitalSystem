package com.example.HospitalSystem.entity.services;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class prices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private Date start_date;
    private Date end_date;

    @ManyToOne
    @JoinColumn(name = "services_id")
    private services service;
}
