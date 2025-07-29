package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.appointments.specialties;
import com.example.HospitalSystem.entity.usersAndRole.doctors;
import com.example.HospitalSystem.repository.custom.SpecieltiesRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtiesRepository extends JpaRepository<specialties, Long>, SpecieltiesRepositoryCustom {

    @Query("SELECT d FROM doctors d JOIN d.specialty s WHERE s.id = :specialtyId")
    Page<doctors> findBySpecialtyId(Long specialtyId, Pageable pageable);
}
