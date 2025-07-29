package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.usersAndRole.emailEncoder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface EmailEncoderRepository extends JpaRepository<emailEncoder, Long> {
//    EmailEncoder saveEmailEncoder(EmailEncoder emailEncoder);
    @Modifying
    @Transactional
    @Query("DELETE FROM emailEncoder e WHERE e.status = true AND e.expiryDate < :expiryDate")
    int deleteByStatusTrueAndExpiryDateBefore(@Param("expiryDate") LocalDateTime expiryDate);
}
