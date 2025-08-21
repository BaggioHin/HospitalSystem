package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.AppointmentRequest;
import com.example.HospitalSystem.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse addAppointment(AppointmentRequest appointmentRequest);

    AppointmentResponse addAppointmentByReception(AppointmentRequest appointmentRequest);

    AppointmentResponse editAppointment(AppointmentRequest appointmentRequest,Long id);

    List<AppointmentResponse> getAllAppointments();

    Void deleteAppointment(Long id);
}
