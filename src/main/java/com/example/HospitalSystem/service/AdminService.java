package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.CreateSchedulesRequest;
import com.example.HospitalSystem.dto.request.UpdatesSchedulesRequest;
import com.example.HospitalSystem.dto.response.SchedulesResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<SchedulesResponse> getSchedules(Long doctorId);

    SchedulesResponse getDetailSchedules(Long Id);

    SchedulesResponse createSchedule(CreateSchedulesRequest request);

    SchedulesResponse UpdateSchedule(UpdatesSchedulesRequest request);

    void deleteSchedule(Long Id);
}
