package com.example.HospitalSystem.entity.files;

import com.example.HospitalSystem.entity.appointments.Specialties;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private String url;
    private Long size;
    private Date upload_at;
    private String linked_entity_type;
    private String linked_entity_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users uploaded_by;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialties specialty;


//    cloud_providers research this problem
}
