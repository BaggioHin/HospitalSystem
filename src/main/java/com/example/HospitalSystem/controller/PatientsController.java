package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.InfPatientRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.PatientInfResponse;
import com.example.HospitalSystem.dto.response.PatientResponse;
import com.example.HospitalSystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientsController {
    @Autowired
    PatientService patientService;

    @GetMapping("/getInfPatient/{id}")
    public ApiResponse<PatientInfResponse> getInfPatient(@PathVariable Long id){
        return ApiResponse.<PatientInfResponse>builder()
                .result(patientService.getPatientInf(id))
                .build();
    }

    @GetMapping("/getListPatient/{username}")
    public ApiResponse<List<PatientResponse>> getListPatients(@PathVariable String username){
        return ApiResponse.<List<PatientResponse>>builder()
                .result(patientService.getListPatient(username))
                .build();
    }

    @PostMapping("/addPatient")
    public ApiResponse<PatientInfResponse> addPatient(@RequestBody InfPatientRequest infPatientRequest){
        return ApiResponse.<PatientInfResponse>builder()
                .result(patientService.addPatient(infPatientRequest))
                .build();
    }

    @PostMapping("/updatePatient/{id}")
    public ApiResponse<PatientInfResponse> updatePatient(@RequestBody InfPatientRequest infPatientRequest,
                                                         @PathVariable Long id){
        return ApiResponse.<PatientInfResponse>builder()
                .result(patientService.updatePatient(infPatientRequest,id))
                .build();
    }

//    @PostMapping("/deletePatient/{id}")
//    public ApiResponse<Void> deletePatient(@PathVariable Long id){
//        return ApiResponse.<Void>builder()
//                .result(patientService.deletePatient(id))
//                .build();
//    }
}
