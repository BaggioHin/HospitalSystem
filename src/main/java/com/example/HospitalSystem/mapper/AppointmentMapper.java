package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.AppointmentRequest;
import com.example.HospitalSystem.dto.response.AppointmentResponse;
import com.example.HospitalSystem.entity.appointments.Appointments;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
//    @Mapping(source = "")
//    Appointments requestToAppointment(AppointmentRequest request);

    AppointmentResponse appointmentToResponse(Appointments appointment);

    Appointments editAppointment(AppointmentRequest appointmentRequest, @MappingTarget Appointments appointment);
}
