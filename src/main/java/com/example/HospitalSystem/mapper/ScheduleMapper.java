package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.CreateSchedulesRequest;
import com.example.HospitalSystem.dto.request.UpdatesSchedulesRequest;
import com.example.HospitalSystem.dto.response.SchedulesResponse;
import com.example.HospitalSystem.entity.appointments.Schedules;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target ="id",ignore = true)
    Schedules requestToSchedules(CreateSchedulesRequest request);

    SchedulesResponse schedulesToResponse(Schedules schedules);

    @Mapping(target ="id",ignore = true)
    Schedules updatesRequestToSchedules(UpdatesSchedulesRequest request,@MappingTarget Schedules entity);

}
