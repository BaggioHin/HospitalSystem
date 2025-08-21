package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.AppointmentStatus;
import com.example.HospitalSystem.dto.request.AppointmentRequest;
import com.example.HospitalSystem.dto.response.AppointmentResponse;
import com.example.HospitalSystem.entity.appointments.Appointments;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Deposits;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import com.example.HospitalSystem.entity.usersAndRole.Patients;
import com.example.HospitalSystem.entity.usersAndRole.Receptionists;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.mapper.AppointmentMapper;
import com.example.HospitalSystem.repository.AppointmentRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @Override
    public AppointmentResponse addAppointment(AppointmentRequest appointmentRequest) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuthToken.getToken();
        Long userId = jwt.getClaim("Id");

        Users user = userRepository.findById(userId).get();
        Patients patients = user.getPatient();
        List<Appointments> appointments = patients.getAppointmentsList();
        Appointments appointment = new Appointments();
        Deposits deposits = new Deposits();
        deposits.setCreated_at(LocalDateTime.now());
        appointment.setDeposits(deposits);
        Invoices invoices = new Invoices();
        appointment.setInvoice(invoices);
        appointment.setDeposits(deposits);
        appointments.add(appointment);
        return appointmentMapper.appointmentToResponse(appointment);
    }

//    Receptionist
    @Override
    public AppointmentResponse addAppointmentByReception(AppointmentRequest appointmentRequest) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuthToken.getToken();
        Long userId = jwt.getClaim("Id");

        Users user = userRepository.findById(userId).get();
        Receptionists receptionists = user.getReceptionist();
        List<Appointments> appointments = receptionists.getAppointmentsList();
        Appointments appointment = new Appointments();
        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointments.add(appointment);
        return appointmentMapper.appointmentToResponse(appointment);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public AppointmentResponse editAppointment(AppointmentRequest appointmentRequest,Long id) {
//        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//
//        org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuthToken.getToken();
//        Long userId = jwt.getClaim("Id");
//
//        Users user = userRepository.findById(userId).get();
//        Patients patients = user.getPatient();
//        List<Appointments> appointments = patients.getAppointmentsList();
        Appointments appointment = appointmentRepository.findById(id).get();
        appointmentMapper.editAppointment(appointmentRequest, appointment);
        appointmentRepository.save(appointment);
//        Appointments appointment = appointmentMapper.requestToAppointment(appointmentRequest);

        return appointmentMapper.appointmentToResponse(appointment);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public List<AppointmentResponse> getAllAppointments() {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuthToken.getToken();
        Long userId = jwt.getClaim("Id");

        Users user = userRepository.findById(userId).get();
        Patients patients = user.getPatient();
        List<Appointments> appointments = patients.getAppointmentsList();
        List<AppointmentResponse> appointmentResponses = appointments.stream().map(appointment ->{
                    AppointmentResponse appointmentResponse = appointmentMapper.appointmentToResponse(appointment);
                    return appointmentResponse;})
                .collect(Collectors.toList());
        return appointmentResponses;
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
        log.info("Delete"+id);
        return null;
    }
}
