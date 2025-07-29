package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.UpdateSpecialty;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.DisplaySpecialtiesResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.dto.response.SpecialtiesResponse;
import com.example.HospitalSystem.service.SpecialtiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialty")
public class SpecialtiesController {
    @Autowired
    SpecialtiesService specialtiesService;

    @GetMapping("/nameSpecialties")
    public ApiResponse<List<DisplaySpecialtiesResponse>> getNameSpecialties() {
        return ApiResponse.<List<DisplaySpecialtiesResponse>>builder()
                .result(specialtiesService.displaySpecialties())
                .build();
    }

    @GetMapping("/InformationSpecialties")
    public ApiResponse<SpecialtiesResponse> getInformationSpecialties(Long id) {
        return ApiResponse.<SpecialtiesResponse>builder()
                .result(specialtiesService.getSpecialties(id))
                .build();
    }

//    @GetMapping("/getInformation")
//    public ApiResponse<Map<String, Page<SpecialtiesResponse>>> getInformation(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return ApiResponse.<Map<String, Page<SpecialtiesResponse>>>builder()
//                .result(specialtiesService.getMultiplePages(page, size))
//                .build();
//    }
    @PostMapping("/update")
    public ApiResponse<SpecialtiesResponse> updateInformationSpecialties(
            @RequestBody UpdateSpecialty updateSpecialty) {
        return ApiResponse.<SpecialtiesResponse>builder()
                .result(specialtiesService.UpdateSpecialties(updateSpecialty))
                .build();
    }

    @GetMapping("/doctor/{id}")
    public ApiResponse<Page<DoctorResponse>> getDoctorSpecialties(@PathVariable Long id,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "15") int size) {
        return ApiResponse.<Page<DoctorResponse>>builder()
                .result(specialtiesService.displayDoctors(id,page,size))
                .build();
    }
}
