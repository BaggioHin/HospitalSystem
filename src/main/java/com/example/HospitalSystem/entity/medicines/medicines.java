package com.example.HospitalSystem.entity.medicines;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class medicines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String dosage_instruction;
    private String usage;
    private String manufacturer;
    private String active_ingredient;
    private Boolean is_active;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private medicineUnits medicine_unit;

    @OneToMany(mappedBy = "medicine",cascade = CascadeType.ALL)
    private List<medicineInventory> medicine_inventories;
}
