package com.example.HospitalSystem.dto.response;

import com.example.HospitalSystem.constant.AppointmentStatus;
import com.example.HospitalSystem.constant.BookingType;
import com.example.HospitalSystem.constant.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long appointmentId;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDateTime;
    private BookingType bookingType;
    private AppointmentStatus status;
    private PaymentStatus paymentStatus;
    private Long depositId;
    private String notes;
}
