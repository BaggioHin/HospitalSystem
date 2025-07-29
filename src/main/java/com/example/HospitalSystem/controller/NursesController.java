package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.InfNurseRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.dto.response.NurseResponse;
import com.example.HospitalSystem.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nurses")
public class NursesController {
    @Autowired
    NurseService nurseService;

    @GetMapping("/infDoctor")
    public ApiResponse<Page<NurseResponse>> getAllNurse(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "15") int size) {
        return ApiResponse.<Page<NurseResponse>>builder()
                .result(nurseService.getAllNurses(page,size))
                .build();
    }

//    @GetMapping("/infDoctor/{id}")
//    public ApiResponse<DoctorDetailResponse> GetDoctorDetail(@PathVariable Long id) {
//        return ApiResponse.<DoctorDetailResponse>builder()
//                .result(nurseService.getDoctorDetail(id))
//                .build();
//    }

    @GetMapping("/ListinfNurse")
    public ApiResponse<List<NurseResponse>> GetAllNurse(String name) {
        return ApiResponse.<List<NurseResponse>>builder()
                .result(nurseService.getListNurse(name))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<NurseResponse> AddDoctorDetail(@RequestBody InfNurseRequest request) {
        return ApiResponse.<NurseResponse>builder()
                .result(nurseService.addNurse(request))
                .build();
    }

    //    care
    @PostMapping("/update/{id}")
    public ApiResponse<NurseResponse> UpdateDoctor(@RequestBody InfNurseRequest request, @PathVariable Long id) {
        return ApiResponse.<NurseResponse>builder()
                .result(nurseService.updateInfNurse(request,id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> DeleteNurse(@PathVariable Long id) {
        return ApiResponse.<Void>builder()
                .result(nurseService.deleteNurse(id))
                .build();
    }

    @PostMapping("/updateStatus/{id}")
    public ApiResponse<Void> UpdateNurseStatus(@PathVariable Long id ){
        return ApiResponse.<Void>builder()
                .result(nurseService.updateNurseStatus(id))
                .build();
    }

}
