package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctors, Long> {
    Doctors findDoctorById(Long id);
}
