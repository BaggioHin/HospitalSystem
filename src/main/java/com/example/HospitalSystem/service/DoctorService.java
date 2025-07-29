package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.InfDoctorRequest;
import com.example.HospitalSystem.dto.response.DoctorDetailResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorDetailResponse getDoctorDetail(Long id);

    List<DoctorResponse> getListDoctor(String name);

    DoctorDetailResponse addDoctor(InfDoctorRequest request);

    DoctorDetailResponse updateInfDoctor(InfDoctorRequest doctorDetail, Long id);

    Void deleteDoctor(Long id);

    Void updateDoctorStatus(Long id);
}
