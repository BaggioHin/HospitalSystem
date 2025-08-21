package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.EmployeeStatus;
import com.example.HospitalSystem.dto.request.InfNurseRequest;
import com.example.HospitalSystem.dto.response.NurseResponse;
import com.example.HospitalSystem.entity.usersAndRole.Nurses;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.mapper.NurseMapper;
import com.example.HospitalSystem.mapper.UserMapper;
import com.example.HospitalSystem.repository.NurseRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.NurseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class NurseServiceImpl implements NurseService {

    @Autowired
    NurseRepository nurseRepository;
    @Autowired
    NurseMapper nurseMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public Page<NurseResponse> getAllNurses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Nurses> nursesPage = nurseRepository.findAll(pageable);

        List<NurseResponse> nurseResponses = nursesPage.getContent().stream()
                .map(nurseMapper::toNurseResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(nurseResponses, pageable, nursesPage.getTotalElements());
    }

    @Override
    public List<NurseResponse> getListNurse(String name) {
        List<Nurses> nursesList = nurseRepository.findNurseWithUserFullName(name);

        List<NurseResponse> nurseResponses = nursesList.stream()
                .map(nurses -> {
                    NurseResponse response = nurseMapper.toNurseResponse(nurses);
                    response.setUsername(
                            nurses.getUser().getFirstName() + " " + nurses.getUser().getLastName()
                    );
                    return response;
                })
                .collect(Collectors.toList());
        return nurseResponses;
    }

    @Override
    public NurseResponse addNurse(InfNurseRequest request) {
        Nurses nurse = nurseMapper.DtotoNurse(request);
        Users user = userMapper.DtoNurseToUser(request);
        userRepository.save(user);
        nurse.setUser(user);
        nurseRepository.save(nurse);
        return nurseMapper.toNurseResponse(nurse);
    }

    @Override
    public NurseResponse updateInfNurse(InfNurseRequest request, Long id) {
        Nurses nurse = nurseRepository.findById(id).get();
        nurseMapper.updateNurse(request,nurse);
        Users user = nurse.getUser();
        userMapper.UpdateUserByNurse(request,user);
        userRepository.save(user);
        nurseRepository.save(nurse);
        return nurseMapper.toNurseResponse(nurse);
    }

    @Override
    public Void updateNurseStatus(Long id) {
        Nurses nurse = nurseRepository.findById(id).get();
        nurse.setStatus(EmployeeStatus.DAY_OFF);
        nurseRepository.save(nurse);
        return null;
    }

    @Override
    public Void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
        log.info("Deleted Nurse " + id);
        return null;
    }

}
