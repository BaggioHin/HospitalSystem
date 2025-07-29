package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfNurseRequest;
import com.example.HospitalSystem.dto.request.InfPatientRequest;
import com.example.HospitalSystem.dto.response.PatientInfResponse;
import com.example.HospitalSystem.dto.response.PatientResponse;
import com.example.HospitalSystem.entity.usersAndRole.nurses;
import com.example.HospitalSystem.entity.usersAndRole.patients;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientInfResponse toPatientInfResponse(patients patients);

    PatientResponse toPatientResponse(patients patients);

    patients DtotoNurse(InfPatientRequest request);

    patients updateNurse(InfPatientRequest infNurseRequest, @MappingTarget patients patients);
}
