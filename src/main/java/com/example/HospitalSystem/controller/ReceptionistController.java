package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.InfReceptionistRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.dto.response.ReceptionistResponse;
import com.example.HospitalSystem.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceptionistController {
    @Autowired
    ReceptionistService receptionistService;

    @GetMapping("/infDoctor")
    public ApiResponse<Page<ReceptionistResponse>> getAllReceptionnist(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "15") int size) {
        return ApiResponse.<Page<ReceptionistResponse>>builder()
                .result(receptionistService.getAllReceptionist(page,size))
                .build();
    }

    @GetMapping("/listInfReceptionist")
    public ApiResponse<List<ReceptionistResponse>> GetAllDoctor(String name) {
        return ApiResponse.<List<ReceptionistResponse>>builder()
                .result(receptionistService.getListReceptionistList(name))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<ReceptionistResponse> AddDoctorDetail(@RequestBody InfReceptionistRequest request) {
        return ApiResponse.<ReceptionistResponse>builder()
                .result(receptionistService.addReceptionist(request))
                .build();
    }

    //    care
    @PostMapping("/update/{id}")
    public ApiResponse<ReceptionistResponse> UpdateDoctor(@RequestBody InfReceptionistRequest request, @PathVariable Long id) {
        return ApiResponse.<ReceptionistResponse>builder()
                .result(receptionistService.updateInfReceptionist(request,id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> DeleteNurse(@PathVariable Long id) {
        return ApiResponse.<Void>builder()
                .result(receptionistService.deleteReceptionist(id))
                .build();
    }

    @PostMapping("/updateStatus/{id}")
    public ApiResponse<Void> UpdateNurseStatus(@PathVariable Long id ){
        return ApiResponse.<Void>builder()
                .result(receptionistService.updateReceptionistStatus(id))
                .build();
    }
}
