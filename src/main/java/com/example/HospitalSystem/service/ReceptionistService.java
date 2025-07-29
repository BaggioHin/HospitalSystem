package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.InfReceptionistRequest;
import com.example.HospitalSystem.dto.response.ReceptionistResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReceptionistService {
    Page<ReceptionistResponse> getAllReceptionist(int page,int size);

    List<ReceptionistResponse> getListReceptionistList(String name);

    ReceptionistResponse addReceptionist(InfReceptionistRequest infReceptionistRequest);

    ReceptionistResponse updateInfReceptionist(InfReceptionistRequest receptionistResponse,Long id);

    Void deleteReceptionist(Long id);
    Void updateReceptionistStatus(Long id);
}
