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
public class AppointmentRequest {
//    private Long patientId;
//    private String name;
    private LocalDateTime date;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private String notes;
    private Long doctorId;
    private Long receptionistId;
}
