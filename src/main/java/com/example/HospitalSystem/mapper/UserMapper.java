package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.*;
import com.example.HospitalSystem.dto.response.UserResponse;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse UserToUserResponse(Users user);

    Users UserRequestToUsers(UserRequest userRequest);

//    users UserToUser(users user);

    Users DtoNurseToUser(InfNurseRequest nurses);

    Users DtoDoctorsToUser(InfDoctorRequest doctors);

    Users DtoReceptionistToUser(InfReceptionistRequest receptionists);

    Users DtoPatientsToUser(InfPatientRequest patients);

    Users UpdateUserByNurse(InfNurseRequest infNurseRequest, @MappingTarget Users users);

    Users UpdateUserByDoctor(InfDoctorRequest infDoctorRequest, @MappingTarget Users users);

    Users UpdateUserByReceptionists(InfReceptionistRequest infReceptionistsRequest, @MappingTarget Users users);

    Users UpdateUserByPatients(InfPatientRequest infPatientsRequest, @MappingTarget Users users);
}
