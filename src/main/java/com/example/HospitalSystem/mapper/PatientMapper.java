package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfPatientRequest;
import com.example.HospitalSystem.dto.response.PatientInfResponse;
import com.example.HospitalSystem.dto.response.PatientResponse;
import com.example.HospitalSystem.entity.usersAndRole.Patients;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientInfResponse toPatientInfResponse(Patients patients);

    PatientResponse toPatientResponse(Patients patients);

    Patients DtotoNurse(InfPatientRequest request);

    Patients updateNurse(InfPatientRequest infNurseRequest, @MappingTarget Patients patients);
}
