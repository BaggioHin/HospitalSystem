package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors, Long> {
    @Query("SELECT d FROM Doctors d WHERE d.id = :id and d.status= 'ACTIVE'")
    Doctors findDoctorById(Long id);

    @Query(value = """
        SELECT d.*
        FROM Doctors d 
        JOIN users u ON u.id = d.user_id 
        WHERE CONCAT(u.firstname, ' ', u.lastname) LIKE %:fullName%
    """, nativeQuery = true)
    List<Doctors> findDoctorsWithUserFullName(@Param("fullName") String fullName);
}
