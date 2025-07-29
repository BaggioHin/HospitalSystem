package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfDoctorRequest;
import com.example.HospitalSystem.dto.response.DoctorDetailResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.entity.usersAndRole.doctors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorResponse toDoctorResponse(doctors doctors);

    DoctorDetailResponse toDoctorDetailResponse(doctors doctors);

    doctors DtotoDoctor(InfDoctorRequest request);

    doctors updateDoctor(InfDoctorRequest updateInfDoctor, @MappingTarget doctors doctors);
}
