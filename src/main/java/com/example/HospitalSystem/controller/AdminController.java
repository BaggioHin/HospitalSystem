package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.CreateSchedulesRequest;
import com.example.HospitalSystem.dto.request.UpdatesSchedulesRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.SchedulesResponse;
import com.example.HospitalSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

//    Get Schedules
    @GetMapping("/getSchedules/{id}")
    ApiResponse<List<SchedulesResponse>> getSchedules(@PathVariable Long userId) {
        return ApiResponse.<List<SchedulesResponse>>builder()
                .result(adminService.getSchedules(userId))
                .build();
    }

//    Get detail Schedules
    @GetMapping("/getSchedules/{id}")
    ApiResponse<SchedulesResponse> getDetailSchedules(@PathVariable Long id) {
        return ApiResponse.<SchedulesResponse>builder()
                .result(adminService.getDetailSchedules(id))
                .build();
    }

//    Add schedules
    @PostMapping("/createSchedule")
    ApiResponse<SchedulesResponse> CreateSchedule(@RequestBody CreateSchedulesRequest request) {
        return ApiResponse.<SchedulesResponse>builder()
                .result(adminService.createSchedule(request))
                .build();
    }

//    Update schedules
    @PostMapping("/updateSchedule")
    ApiResponse<SchedulesResponse> UpdateSchedule(@RequestBody UpdatesSchedulesRequest request) {
        return ApiResponse.<SchedulesResponse>builder()
                .result(adminService.UpdateSchedule(request))
                .build();
    }

//  Delete schedules
    @DeleteMapping("/deleteSchedeles/{id}")
    void deleteSchedule(@PathVariable Long id) {
        adminService.deleteSchedule(id);
    }
}
