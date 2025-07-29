package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.receptionists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptionistRepository extends JpaRepository<receptionists, Long> {
    Page<receptionists> findAll(Pageable pageable);

    @Query(value = """
        SELECT n.*
        FROM  receptionists n
        JOIN users u ON u.id = n.user_id 
        WHERE CONCAT(u.firstname, ' ', u.lastname) LIKE %:fullName%
    """, nativeQuery = true)
    List<receptionists> findReceptionistWithUserFullName(String fullName);
}
