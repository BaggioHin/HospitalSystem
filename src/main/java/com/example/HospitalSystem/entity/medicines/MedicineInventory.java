package com.example.HospitalSystem.entity.medicines;

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
public class MedicineInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private String batch_number;
    private Date expiry_date;
    private Date import_date;
    private BigDecimal price_per_unit;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicines medicine;
}
