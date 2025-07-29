package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.UpdateSpecialty;
import com.example.HospitalSystem.dto.response.DisplaySpecialtiesResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.dto.response.SpecialtiesResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SpecialtiesService {
    List<DisplaySpecialtiesResponse> displaySpecialties();
//    public Map<String, Page<SpecialtiesResponse>> getMultiplePages(int currentPage, int size);
    SpecialtiesResponse getSpecialties(Long id);
    SpecialtiesResponse UpdateSpecialties(UpdateSpecialty updateSpecialty);
    Page<DoctorResponse> displayDoctors(Long id, int page, int size);
}
