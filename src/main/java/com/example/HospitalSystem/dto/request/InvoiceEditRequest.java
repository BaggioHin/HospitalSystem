package com.example.HospitalSystem.dto.request;

import com.example.HospitalSystem.constant.PaymentStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEditRequest {
    private Long total_amount;
    private PaymentStatus paymentStatus;
    private Date created_at;
    private LocalDate paid_at;
    private String description;
}
