package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.appointments.Specialties;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import com.example.HospitalSystem.repository.custom.SpecieltiesRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtiesRepository extends JpaRepository<Specialties, Long>, SpecieltiesRepositoryCustom {

    @Query("SELECT d FROM Doctors d JOIN d.specialty s WHERE s.id = :specialtyId")
    Page<Doctors> findBySpecialtyId(Long specialtyId, Pageable pageable);
}
