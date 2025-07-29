package com.example.HospitalSystem.entity.appointments;

import com.example.HospitalSystem.entity.files.files;
import com.example.HospitalSystem.entity.usersAndRole.doctors;
import com.example.HospitalSystem.entity.usersAndRole.nurses;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class specialties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private String image_url;

    @OneToMany(mappedBy = "specialty")
    private List<nurses> nursesList;

    @OneToMany(mappedBy = "specialty")
    private List<doctors> doctorsList;

    @OneToMany(mappedBy = "specialty")
    private List<files> filesList;

    @OneToMany(mappedBy = "specialty")
    private List<schedules> schedulesList;
}
