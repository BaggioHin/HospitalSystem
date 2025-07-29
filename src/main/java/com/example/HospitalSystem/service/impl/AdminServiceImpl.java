package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.dto.request.CreateSchedulesRequest;
import com.example.HospitalSystem.dto.request.UpdatesSchedulesRequest;
import com.example.HospitalSystem.dto.response.SchedulesResponse;
import com.example.HospitalSystem.entity.appointments.schedules;
import com.example.HospitalSystem.entity.usersAndRole.doctors;
import com.example.HospitalSystem.mapper.ScheduleMapper;
import com.example.HospitalSystem.repository.DoctorRepository;
import com.example.HospitalSystem.repository.ScheduleRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public List<SchedulesResponse> getSchedules(Long doctorId) {
        doctors doctors = doctorRepository.findById(doctorId).get();
        List<schedules> schedules = doctors.getSchedulesList();
        List<SchedulesResponse> responses = schedules.stream()
                .map(scheduleMapper::schedulesToResponse)
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public SchedulesResponse getDetailSchedules(Long Id) {
        schedules schedules = scheduleRepository.findById(Id).get();
        return scheduleMapper.schedulesToResponse(schedules);
    }

    @Override
    public SchedulesResponse createSchedule(CreateSchedulesRequest request) {
        doctors doctors = doctorRepository.findDoctorById(request.getId());
        List<schedules> schedules = doctors.getSchedulesList();
        com.example.HospitalSystem.entity.appointments.schedules schedule = scheduleMapper.requestToSchedules(request);
        scheduleRepository.save(schedule);
        schedules.add(schedule);
        return scheduleMapper.schedulesToResponse(schedule);
    }

//    @PreAuthorize("isAuthenticated() and #request.userId == authentication.principal.id")
    @Override
    public SchedulesResponse UpdateSchedule(UpdatesSchedulesRequest request) {
        schedules schedules = scheduleRepository.findById(request.getId()).get();
        scheduleMapper.updatesRequestToSchedules(request,schedules);
        scheduleRepository.save(schedules);
        return scheduleMapper.schedulesToResponse(schedules);
    }

    @Override
    public void deleteSchedule(Long Id) {
        scheduleRepository.deleteById(Id);
        log.info("Deleted Schedule :" + Id);
    }
}
