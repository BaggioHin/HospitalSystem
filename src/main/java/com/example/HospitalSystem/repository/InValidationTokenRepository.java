package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.invalidationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InValidationTokenRepository extends JpaRepository<invalidationTokenEntity,Long> {
    boolean existsById(String id);
}
