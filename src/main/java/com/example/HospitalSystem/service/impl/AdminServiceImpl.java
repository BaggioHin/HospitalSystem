package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.dto.request.CreateSchedulesRequest;
import com.example.HospitalSystem.dto.request.UpdatesSchedulesRequest;
import com.example.HospitalSystem.dto.response.SchedulesResponse;
import com.example.HospitalSystem.entity.appointments.Schedules;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
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
        Doctors Doctors = doctorRepository.findById(doctorId).get();
        List<Schedules> schedules = Doctors.getSchedulesList();
        List<SchedulesResponse> responses = schedules.stream()
                .map(scheduleMapper::schedulesToResponse)
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    public SchedulesResponse getDetailSchedules(Long Id) {
        Schedules schedules = scheduleRepository.findById(Id).get();
        return scheduleMapper.schedulesToResponse(schedules);
    }

    @Override
    public SchedulesResponse createSchedule(CreateSchedulesRequest request) {
        Doctors Doctors = doctorRepository.findDoctorById(request.getId());
        List<Schedules> schedules = Doctors.getSchedulesList();
        Schedules schedule = scheduleMapper.requestToSchedules(request);
        scheduleRepository.save(schedule);
        schedules.add(schedule);
        return scheduleMapper.schedulesToResponse(schedule);
    }

//    @PreAuthorize("isAuthenticated() and #request.userId == authentication.principal.id")
    @Override
    public SchedulesResponse UpdateSchedule(UpdatesSchedulesRequest request) {
        Schedules schedules = scheduleRepository.findById(request.getId()).get();
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
