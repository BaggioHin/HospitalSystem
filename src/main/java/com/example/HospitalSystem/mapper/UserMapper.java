package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.*;
import com.example.HospitalSystem.dto.response.UserResponse;
import com.example.HospitalSystem.entity.usersAndRole.users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse UserToUserResponse(users user);

    users UserRequestToUsers(UserRequest userRequest);

//    users UserToUser(users user);

    users DtoNurseToUser(InfNurseRequest nurses);

    users DtoDoctorsToUser(InfDoctorRequest doctors);

    users DtoReceptionistToUser(InfReceptionistRequest receptionists);

    users DtoPatientsToUser(InfPatientRequest patients);

    users UpdateUserByNurse(InfNurseRequest infNurseRequest, @MappingTarget users users);

    users UpdateUserByDoctor(InfDoctorRequest infDoctorRequest, @MappingTarget users users);

    users UpdateUserByReceptionists(InfReceptionistRequest infReceptionistsRequest, @MappingTarget users users);

    users UpdateUserByPatients(InfPatientRequest infPatientsRequest, @MappingTarget users users);
}
