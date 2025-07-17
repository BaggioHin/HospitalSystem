package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.appointments.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedules,Long> {
}
