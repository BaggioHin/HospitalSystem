package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.UpdateSpecialty;
import com.example.HospitalSystem.dto.response.SpecialtiesResponse;
import com.example.HospitalSystem.entity.appointments.specialties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpecialtiesMapper {

    SpecialtiesResponse specialtiesToResponse(specialties specialties);

    @Mapping(target ="id",ignore = true)
    specialties updateSpecialtyFromResquest(UpdateSpecialty updateSpecialty,@MappingTarget specialties specialties);
}
