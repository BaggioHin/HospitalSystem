package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<users, Long> {
    Optional<users> findByUsername(String username);
    Optional<users> findById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<users> findByEmail(String email);
}
