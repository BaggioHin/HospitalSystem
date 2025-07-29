package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.dto.request.InfPatientRequest;
import com.example.HospitalSystem.dto.response.PatientInfResponse;
import com.example.HospitalSystem.dto.response.PatientResponse;
import com.example.HospitalSystem.entity.usersAndRole.nurses;
import com.example.HospitalSystem.entity.usersAndRole.patients;
import com.example.HospitalSystem.entity.usersAndRole.users;
import com.example.HospitalSystem.mapper.PatientMapper;
import com.example.HospitalSystem.mapper.UserMapper;
import com.example.HospitalSystem.repository.PatientRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public PatientInfResponse getPatientInf(Long patientId) {
        patients patients = patientRepository.findById(patientId).get();
        return patientMapper.toPatientInfResponse(patients);
    }

    @Override
    public List<PatientResponse> getListPatient(String name) {
        List<patients> patients = patientRepository.findPatientsWithUserFullName(name);
        List<PatientResponse> patientResponses = patients.stream()
                .map(patient ->{PatientResponse patientResponse=new PatientResponse();
                    patientResponse.setFirstName(patient.getUser().getFirstName());
                    patientResponse.setLastName(patient.getUser().getLastName());
                    patientResponse.setProfileImageUrl(patient.getProfileImageUrl());
                    return patientResponse;
                } )
                .collect(Collectors.toList());
        return patientResponses;
    }

    @Override
    public PatientInfResponse addPatient(InfPatientRequest request) {
        patients patient = patientMapper.DtotoNurse(request);
        users user = userMapper.DtoPatientsToUser(request);
        userRepository.save(user);
        patient.setUser(user);
        patientRepository.save(patient);
        return patientMapper.toPatientInfResponse(patient);
    }

    @Override
    public PatientInfResponse updatePatient(InfPatientRequest request,Long id) {
        patients patient = patientRepository.findById(id).get();
        patientMapper.updateNurse(request,patient);
        users user = patient.getUser();
        userMapper.UpdateUserByPatients(request,user);
        userRepository.save(user);
        patientRepository.save(patient);
        return patientMapper.toPatientInfResponse(patient);
    }

//    @Override
//    public Void deletePatient(Long patientId) {
//
//    }
}
