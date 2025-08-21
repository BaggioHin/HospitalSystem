package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.UpdateSpecialty;
import com.example.HospitalSystem.dto.response.SpecialtiesResponse;
import com.example.HospitalSystem.entity.appointments.Specialties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpecialtiesMapper {

    SpecialtiesResponse specialtiesToResponse(Specialties specialties);

    @Mapping(target ="id",ignore = true)
    Specialties updateSpecialtyFromResquest(UpdateSpecialty updateSpecialty, @MappingTarget Specialties specialties);
}
