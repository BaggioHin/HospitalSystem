package com.example.HospitalSystem.dto.response;

import com.example.HospitalSystem.constant.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {
    private PaymentStatus paymentStatus;
    private LocalDateTime created_at;
    private String paymentUrl;
}
