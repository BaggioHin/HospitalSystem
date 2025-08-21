package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.response.PaymentResultResponse;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Payments;

import java.util.Map;

public interface PaymentService {
    String createPayment(Payments payment, String clientIp);

    PaymentResultResponse handleVNPayReturn(Map<String, String> params);
}
