package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.EmployeeStatus;
import com.example.HospitalSystem.dto.request.InfDoctorRequest;
import com.example.HospitalSystem.dto.response.DoctorDetailResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.mapper.DoctorMapper;
import com.example.HospitalSystem.mapper.UserMapper;
import com.example.HospitalSystem.repository.DoctorRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public DoctorDetailResponse getDoctorDetail(Long id) {
        Doctors Doctors = doctorRepository.findDoctorById(id);
        return doctorMapper.toDoctorDetailResponse(Doctors);
    }

    @Override
    public List<DoctorResponse> getListDoctor(String name) {
        List<Doctors> doctorsList = doctorRepository.findDoctorsWithUserFullName(name);

        List<DoctorResponse> doctorResponses = doctorsList.stream()
                .map(doctor -> {
                    DoctorResponse response = doctorMapper.toDoctorResponse(doctor);
                    response.setName(
                            doctor.getUser().getFirstName() + " " + doctor.getUser().getLastName()
                    );
                    return response;
                })
                .collect(Collectors.toList());
        return doctorResponses;
    }

    @Override
    public DoctorDetailResponse addDoctor(InfDoctorRequest request) {
        Doctors Doctors= doctorMapper.DtotoDoctor(request);
        Users user = userMapper.DtoDoctorsToUser(request);
        userRepository.save(user);
        Doctors.setUser(user);
        doctorRepository.save(Doctors);
        return doctorMapper.toDoctorDetailResponse(Doctors);
    }

    @PreAuthorize("isAuthenticated() and #id == authentication.principal.id")
    @Override
    public DoctorDetailResponse updateInfDoctor(InfDoctorRequest doctorDetail, Long id) {
        Doctors Doctors = doctorRepository.findById(id).get();
        doctorMapper.updateDoctor(doctorDetail,Doctors);
        Users user = Doctors.getUser();
        userMapper.UpdateUserByDoctor(doctorDetail,user);
        userRepository.save(user);
        doctorRepository.save(Doctors);
        return doctorMapper.toDoctorDetailResponse(Doctors);
    }

    @Override
    public Void deleteDoctor(Long id) {
        Doctors Doctors = doctorRepository.findDoctorById(id);
        Doctors.setStatus(EmployeeStatus.ON_LEAVE);
        log.info("Successfull Delete Doctor " + id);
        return null;
    }

    @Override
    public Void updateDoctorStatus(Long id) {
        Doctors Doctors = doctorRepository.findDoctorById(id);
        Doctors.setStatus(EmployeeStatus.DAY_OFF);
        log.info("Successfull Update Doctor " + id);
        return null;
    }
}
