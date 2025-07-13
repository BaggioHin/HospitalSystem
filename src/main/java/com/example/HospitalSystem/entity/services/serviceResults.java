package com.example.HospitalSystem.entity.services;

import com.example.HospitalSystem.entity.appointments.appointments;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class serviceResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String result_text;
    private Date result_date;
    private String doctor_note;

    @ManyToOne
    @JoinColumn(name = "services_id")
    private services services;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private appointments appointment;

    @OneToMany(mappedBy = "serviceResult")
    private List<serviceImages> serviceImages;
}
