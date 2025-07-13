package com.example.HospitalSystem.service;
import com.example.HospitalSystem.dto.request.UserRequest;
import com.example.HospitalSystem.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserByUsername(String username);
    UserResponse createUser(UserRequest userRequest);
    UserResponse getInfo();
    UserResponse updateUser(UserRequest userRequest);
    void deleteUser(Long id);
}
