package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.InfDoctorRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.DoctorDetailResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/infDoctor/{id}")
    public ApiResponse<DoctorDetailResponse> GetDoctorDetail(@PathVariable Long id) {
        return ApiResponse.<DoctorDetailResponse>builder()
                .result(doctorService.getDoctorDetail(id))
                .build();
    }

    @GetMapping("/listInfDoctor")
    public ApiResponse<List<DoctorResponse>> GetAllDoctor(String name) {
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(doctorService.getListDoctor(name))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<DoctorDetailResponse> AddDoctorDetail(@RequestBody InfDoctorRequest doctorDetail){
        return ApiResponse.<DoctorDetailResponse>builder()
                .result(doctorService.addDoctor(doctorDetail))
                .build();
    }

//    care
    @PostMapping("/update/{id}")
    public ApiResponse<DoctorDetailResponse> UpdateDoctor(@RequestBody InfDoctorRequest doctorDetail, @PathVariable Long id) {
        return ApiResponse.<DoctorDetailResponse>builder()
                .result(doctorService.updateInfDoctor(doctorDetail,id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> DeleteDoctor(@PathVariable Long id) {
        return ApiResponse.<Void>builder()
                .result(doctorService.deleteDoctor(id))
                .build();
    }

    @PostMapping("/updateStatus/{id}")
    public ApiResponse<Void> UpdateDoctorStatus(@PathVariable Long id ){
        return ApiResponse.<Void>builder()
                .result(doctorService.updateDoctorStatus(id))
                .build();
    }
}
