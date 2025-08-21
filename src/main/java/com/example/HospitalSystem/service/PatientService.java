package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.InfPatientRequest;
import com.example.HospitalSystem.dto.response.PatientInfResponse;
import com.example.HospitalSystem.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientInfResponse getPatientInf(Long patientId);

    List<PatientResponse> getListPatient(String name);

    PatientInfResponse addPatient(InfPatientRequest request);

    PatientInfResponse updatePatient(InfPatientRequest request,Long id);

    Void deletePatient(Long patientId);
}
