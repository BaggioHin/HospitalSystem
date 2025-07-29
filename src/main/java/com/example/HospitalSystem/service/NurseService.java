package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.InfNurseRequest;
import com.example.HospitalSystem.dto.response.NurseResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface NurseService {
    Page<NurseResponse> getAllNurses(int page, int size);

    List<NurseResponse> getListNurse(String name);

    NurseResponse addNurse(InfNurseRequest request);

    NurseResponse updateInfNurse(InfNurseRequest request,Long id);

    Void updateNurseStatus(Long id);

    Void deleteNurse(Long id);
}
