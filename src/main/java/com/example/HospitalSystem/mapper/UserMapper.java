package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.UserRequest;
import com.example.HospitalSystem.dto.response.UserResponse;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse UserToUserResponse(Users user);

    Users UserRequestToUsers(UserRequest userRequest);
}
