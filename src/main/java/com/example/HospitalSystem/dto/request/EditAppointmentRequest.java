package com.example.HospitalSystem.dto.request;

import com.example.HospitalSystem.constant.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditAppointmentRequest {
    private LocalDateTime date;
    private PaymentStatus status = PaymentStatus.PENDING;
    private String notes;
    private Long doctorId;
    private Long receptionistId;
}
