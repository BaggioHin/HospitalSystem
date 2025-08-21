package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.AppointmentRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.AppointmentResponse;
import com.example.HospitalSystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/create")
    public ApiResponse<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest appointmentRequest){
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.addAppointment(appointmentRequest))
                .build();
    }

    @PostMapping("/createAppointment")
    public ApiResponse<AppointmentResponse> createAppointmentByReception(@RequestBody AppointmentRequest appointmentRequest){
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.addAppointmentByReception(appointmentRequest))
                .build();
    }

//    @PostMapping("/deposit")
//    public ApiResponse<DepositResponse> depositAppointment(){
//        return ApiResponse.<DepositResponse>builder()
//                .result(depositService.addDeposit())
//                .build();
//    }

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments(){
        return ApiResponse.<List<AppointmentResponse>>builder()
                .result(appointmentService.getAllAppointments())
                .build();
    }

    @PutMapping("/Edit")
    public ApiResponse<AppointmentResponse> editAppointment(@RequestBody AppointmentRequest appointmentRequest,
                                                            @RequestParam Long id){
        return ApiResponse.<AppointmentResponse>builder()
                .result(appointmentService.editAppointment(appointmentRequest,id))
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteAppointment(@RequestParam Long appointmentId){
        return ApiResponse.<Void>builder()
                .result(appointmentService.deleteAppointment(appointmentId))
                .build();
    }


}
