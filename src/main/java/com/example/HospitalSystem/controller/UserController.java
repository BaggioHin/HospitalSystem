package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.UserRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.UserResponse;
import com.example.HospitalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/SignUp")
    ApiResponse<UserResponse> SignUp(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
    }

    @GetMapping("/Info")
    ApiResponse<UserResponse> GetInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getInfo())
                .build();
    }

    @PostMapping("/Update")
    ApiResponse<UserResponse> Update(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.updateUser(userRequest);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
    }

    @PostMapping("/Delete/{id}")
    void Delete(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
