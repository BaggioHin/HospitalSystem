package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.appointments.schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<schedules,Long> {
}
