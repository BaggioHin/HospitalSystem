package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.appointments.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedules,Long> {
}
