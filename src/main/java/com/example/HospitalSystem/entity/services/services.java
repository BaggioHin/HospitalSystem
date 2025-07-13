package com.example.HospitalSystem.entity.services;

import com.example.HospitalSystem.entity.paymentsAndInvoices.invoiceItems;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String type;
    private Integer duration_minutes;
    private Boolean is_active;

    @OneToMany(mappedBy = "service")
    private List<prices> prices;

    @OneToMany(mappedBy = "services")
    private List<serviceResults> serviceResults;

    @OneToOne(mappedBy = "service")
    private invoiceItems invoiceItems;
}
