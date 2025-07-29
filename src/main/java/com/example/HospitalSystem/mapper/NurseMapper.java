package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfNurseRequest;
import com.example.HospitalSystem.dto.response.NurseResponse;
import com.example.HospitalSystem.entity.usersAndRole.nurses;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NurseMapper {
    NurseResponse toNurseResponse(nurses nurse);

    nurses DtotoNurse(InfNurseRequest request);

    nurses updateNurse(InfNurseRequest infNurseRequest, @MappingTarget nurses nurses);

}
