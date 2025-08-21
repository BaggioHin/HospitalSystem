package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.EmployeeStatus;
import com.example.HospitalSystem.dto.request.InfReceptionistRequest;
import com.example.HospitalSystem.dto.response.ReceptionistResponse;
import com.example.HospitalSystem.entity.usersAndRole.Receptionists;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.mapper.ReceptionistMapper;
import com.example.HospitalSystem.mapper.UserMapper;
import com.example.HospitalSystem.repository.ReceptionistRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.ReceptionistService;
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
public class ReceptionistServiceImpl implements ReceptionistService {

    @Autowired
    ReceptionistRepository receptionistRepository;
    @Autowired
    ReceptionistMapper receptionistMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public Page<ReceptionistResponse> getAllReceptionist(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Receptionists> receptionistsPage = receptionistRepository.findAll(pageable);

        List<ReceptionistResponse> receptionistsResponses =  receptionistsPage.getContent().stream()
                .map(receptionistMapper::toReceptionistResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(receptionistsResponses, pageable,  receptionistsPage.getTotalElements());
    }

    @Override
    public List<ReceptionistResponse> getListReceptionistList(String name) {
        List<Receptionists> receptionists = receptionistRepository.findReceptionistWithUserFullName(name);

        List<ReceptionistResponse> receptionistResponses = receptionists.stream()
                .map(receptionist -> {
                    ReceptionistResponse response = receptionistMapper.toReceptionistResponse(receptionist);
                    response.setUsername(
                            receptionist.getUser().getFirstName() + " " + receptionist.getUser().getLastName()
                    );
                    return response;
                })
                .collect(Collectors.toList());
        return receptionistResponses;
    }

    @Override
    public ReceptionistResponse addReceptionist(InfReceptionistRequest infReceptionistRequest) {
        Receptionists receptionist = receptionistMapper.DtotoReceptionist(infReceptionistRequest);
        Users user = userMapper.DtoReceptionistToUser(infReceptionistRequest);
        userRepository.save(user);
        receptionist.setUser(user);
        receptionistRepository.save(receptionist);
        return receptionistMapper.toReceptionistResponse(receptionist);
    }

    @Override
    public ReceptionistResponse updateInfReceptionist(InfReceptionistRequest infReceptionistRequest, Long id) {
        Receptionists receptionist = receptionistRepository.findById(id).get();
        receptionistMapper.updateReceptionist(infReceptionistRequest,receptionist);
        Users user = receptionist.getUser();
        userMapper.UpdateUserByReceptionists(infReceptionistRequest,user);
        userRepository.save(user);
        receptionistRepository.save(receptionist);
        return receptionistMapper.toReceptionistResponse(receptionist);
    }

    @Override
    public Void deleteReceptionist(Long id) {
        Receptionists receptionists = receptionistRepository.findById(id).get();
        receptionists.setStatus(EmployeeStatus.DAY_OFF);
        receptionistRepository.save(receptionists);
        return null;
    }

    @Override
    public Void updateReceptionistStatus(Long id) {
        receptionistRepository.deleteById(id);
        log.info("Deleted Nurse " + id);
        return null;
    }
}
