package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfNurseRequest;
import com.example.HospitalSystem.dto.response.NurseResponse;
import com.example.HospitalSystem.entity.usersAndRole.Nurses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NurseMapper {
    NurseResponse toNurseResponse(Nurses nurse);

    @Mapping(target = "specialty",ignore=true)
    Nurses DtotoNurse(InfNurseRequest request);

    @Mapping(target = "specialty",ignore=true)
    Nurses updateNurse(InfNurseRequest infNurseRequest, @MappingTarget Nurses nurses);

}
