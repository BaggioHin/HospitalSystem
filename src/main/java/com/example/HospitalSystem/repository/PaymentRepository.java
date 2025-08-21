package com.example.HospitalSystem.repository;

import com.example.HospitalSystem.entity.paymentsAndInvoices.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments,Long> {
    Optional<Payments> findByVnpTxnRef(String vnpTxnRef);
}
