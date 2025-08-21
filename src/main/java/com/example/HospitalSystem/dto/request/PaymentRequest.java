package com.example.HospitalSystem.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long amount;
    private String orderInfo;
    private String ipAddress;
}
