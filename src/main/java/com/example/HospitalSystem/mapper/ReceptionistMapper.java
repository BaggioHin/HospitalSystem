package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InfReceptionistRequest;
import com.example.HospitalSystem.dto.response.ReceptionistResponse;
import com.example.HospitalSystem.entity.usersAndRole.Receptionists;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceptionistMapper {
    ReceptionistResponse toReceptionistResponse(Receptionists receptionists);

    Receptionists DtotoReceptionist(InfReceptionistRequest request);

    Receptionists updateReceptionist(InfReceptionistRequest infReceptionistRequest, @MappingTarget Receptionists receptionists);
}
