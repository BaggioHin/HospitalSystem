//package com.example.HospitalSystem.service.impl;
//
//import com.example.HospitalSystem.dto.response.DepositResponse;
//import com.example.HospitalSystem.entity.appointments.Appointments;
//import com.example.HospitalSystem.entity.paymentsAndInvoices.Deposits;
//import com.example.HospitalSystem.entity.usersAndRole.Patients;
//import com.example.HospitalSystem.entity.usersAndRole.Users;
//import com.example.HospitalSystem.repository.DepositRepository;
//import com.example.HospitalSystem.repository.UserRepository;
//import com.example.HospitalSystem.service.DepositService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DepositServiceImpl implements DepositService {
//    @Autowired
//    DepositRepository depositRepository;
//    @Autowired
//    UserRepository userRepository;
//
//    @PreAuthorize("isAuthenticated()")
//    @Override
//    public DepositResponse addDeposit() {
//        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//
//        org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuthToken.getToken();
//        Long userId = jwt.getClaim("Id");
//
//        Users users = userRepository.findById(userId).get();
//        Patients patients  = users.getPatient();
//        Appointments appointments = new Appointments();
//        Deposits deposits = appointments.getDeposits();
//        patients.getAppointmentsList().add(appointments);
//
//    }
//}
