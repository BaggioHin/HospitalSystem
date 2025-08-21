package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.entity.files.files;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import com.example.HospitalSystem.entity.usersAndRole.Nurses;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Specialties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private String image_url;

    @OneToMany(mappedBy = "specialty")
    private List<Nurses> nursesList;

    @OneToMany(mappedBy = "specialty")
    private List<Doctors> doctorsList;

    @OneToMany(mappedBy = "specialty")
    private List<files> filesList;

    @OneToMany(mappedBy = "specialties")
    private List<Schedules> schedulesList;
}
