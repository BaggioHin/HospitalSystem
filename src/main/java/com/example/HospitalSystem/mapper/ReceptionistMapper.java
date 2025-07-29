package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfReceptionistRequest;
import com.example.HospitalSystem.dto.response.ReceptionistResponse;
import com.example.HospitalSystem.entity.usersAndRole.receptionists;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceptionistMapper {
    ReceptionistResponse toReceptionistResponse(receptionists receptionists);

    receptionists DtotoReceptionist(InfReceptionistRequest request);

    receptionists updateReceptionist(InfReceptionistRequest infReceptionistRequest, @MappingTarget receptionists receptionists);
}
