package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.paymentsAndInvoices.Deposits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposits,Long> {
}
