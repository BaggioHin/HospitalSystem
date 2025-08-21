package com.example.HospitalSystem.dto.response;

import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.constant.PaymentVerificationStatus;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultResponse {
    private PaymentStatus paymentStatus;
    private PaymentVerificationStatus verificationStatus;
    private long amount;
    private String transactionNo;
    private String invoiceId;
    private String paymentTime;
}
