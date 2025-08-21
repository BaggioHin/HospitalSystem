package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.appointments.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments,Long> {
    void deleteById(Long id);
}
