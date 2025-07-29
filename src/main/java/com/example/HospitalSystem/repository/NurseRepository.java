package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.nurses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<nurses,Long> {
    Page<nurses> findAll(Pageable pageable);

    @Query(value = """
        SELECT n.*
        FROM nurses n
        JOIN users u ON u.id = n.user_id 
        WHERE CONCAT(u.firstname, ' ', u.lastname) LIKE %:fullName%
    """, nativeQuery = true)
    List<nurses>  findNurseWithUserFullName(String fullName);
}
