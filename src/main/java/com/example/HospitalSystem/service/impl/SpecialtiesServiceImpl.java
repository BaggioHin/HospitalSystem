package com.example.HospitalSystem.service.impl;
import com.example.HospitalSystem.dto.request.UpdateSpecialty;
import com.example.HospitalSystem.dto.response.DisplaySpecialtiesResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.dto.response.SpecialtiesResponse;
import com.example.HospitalSystem.entity.appointments.Specialties;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import com.example.HospitalSystem.mapper.DoctorMapper;
import com.example.HospitalSystem.mapper.SpecialtiesMapper;
import com.example.HospitalSystem.repository.SpecialtiesRepository;
import com.example.HospitalSystem.service.SpecialtiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtiesServiceImpl implements SpecialtiesService {

    @Autowired
    SpecialtiesRepository specialtiesRepository;

    @Autowired
    SpecialtiesMapper specialtiesMapper;

    @Autowired
    DoctorMapper doctorMapper;

    @Override
    public List<DisplaySpecialtiesResponse> displaySpecialties() {
        List<Specialties> specialties = specialtiesRepository.findAll();
        List<DisplaySpecialtiesResponse> responses = specialties.stream()
                .map(specialty -> {
                    return new DisplaySpecialtiesResponse(
                            specialty.getId(),
                            specialty.getName()
                    );
                })
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public SpecialtiesResponse getSpecialties(Long id) {
        Specialties specialties = specialtiesRepository.findById(id).get();
        return specialtiesMapper.specialtiesToResponse(specialties);
    }

    @Override
    public SpecialtiesResponse UpdateSpecialties(UpdateSpecialty updateSpecialty) {
        Long id = updateSpecialty.getId();
        Specialties specialtie = specialtiesRepository.findById(id).get();
        specialtiesRepository.save(specialtiesMapper
                .updateSpecialtyFromResquest(updateSpecialty,specialtie));
        return specialtiesMapper.specialtiesToResponse(specialtie);
    }

    @Override
    public Page<DoctorResponse> displayDoctors(Long id, int currentPage, int size) {
//        int totalPages = specialtiesRepository.findAll(PageRequest.of(0, size)).getTotalPages();

        Pageable pageable = PageRequest.of(currentPage, size);
        Page<Doctors> doctorPage = specialtiesRepository.findBySpecialtyId(id, pageable);

        List<DoctorResponse> doctorResponses = doctorPage.getContent().stream()
                .map(doctorMapper::toDoctorResponse)
                .collect(Collectors.toList());

//        Map<String, List<DoctorResponse>> pages = new LinkedHashMap<>();
//        pages.put("page_" + currentPage, Doctors);
//
//        PaginatedDoctorsResponse.Meta meta = new PaginatedDoctorsResponse.Meta(
//                currentPage,
//                doctorPage.getTotalPages(),
//                size
//        );

        return new PageImpl<>(doctorResponses, pageable, doctorPage.getTotalElements());
    }


//    public Map<String, Page<SpecialtiesResponse>> getMultiplePages(int currentPage, int size) {
//        int totalPages = specialtiesRepository.findAll(PageRequest.of(0, size)).getTotalPages();
//
//        List<Integer> pagesToLoad = new ArrayList<>();
//
//        pagesToLoad.add(currentPage);
//
//        if (currentPage - 2 > 0) pagesToLoad.add(currentPage - 2);
//        if (currentPage - 1 > 0) pagesToLoad.add(currentPage - 1);
//        if (currentPage + 1 < totalPages) pagesToLoad.add(currentPage + 1);
//        if (currentPage + 2 < totalPages) pagesToLoad.add(currentPage + 2);
//
//        if (totalPages > 0) pagesToLoad.add(totalPages - 1);
//
//        pagesToLoad = pagesToLoad.stream().distinct().sorted().collect(Collectors.toList());
//
//        Map<String, Page<SpecialtiesResponse>> result = new LinkedHashMap<>();
//        for (Integer pageNum : pagesToLoad) {
//            Page<specialties> pageData = specialtiesRepository.findAll(PageRequest.of(pageNum, size));
//            Page<SpecialtiesResponse> responsePage = pageData.map(specialtiesMapper::specialtiesToResponse);
//            result.put("page_" + pageNum, responsePage);
//        }
//
//        return result;
//    }

}
