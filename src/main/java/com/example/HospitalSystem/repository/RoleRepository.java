package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<roles, String> {

}
