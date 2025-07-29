package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.nurses;
import com.example.HospitalSystem.entity.usersAndRole.patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<patients,Long> {
    @Query(value = """
        SELECT n.*
        FROM patients n
        JOIN users u ON u.id = n.user_id 
        WHERE CONCAT(u.firstname, ' ', u.lastname) LIKE %:fullName%
    """, nativeQuery = true)
    List<patients> findPatientsWithUserFullName(String fullName);
}
