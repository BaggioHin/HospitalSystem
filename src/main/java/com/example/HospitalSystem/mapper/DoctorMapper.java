package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfDoctorRequest;
import com.example.HospitalSystem.dto.response.DoctorDetailResponse;
import com.example.HospitalSystem.dto.response.DoctorResponse;
import com.example.HospitalSystem.entity.usersAndRole.Doctors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorResponse toDoctorResponse(Doctors Doctors);

    DoctorDetailResponse toDoctorDetailResponse(Doctors Doctors);

    Doctors DtotoDoctor(InfDoctorRequest request);

    Doctors updateDoctor(InfDoctorRequest updateInfDoctor, @MappingTarget Doctors Doctors);
}
