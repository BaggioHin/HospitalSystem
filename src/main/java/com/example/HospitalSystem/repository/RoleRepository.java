package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

}
