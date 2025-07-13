package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.dto.request.SignIn;
import com.example.HospitalSystem.dto.request.UserRequest;
import com.example.HospitalSystem.dto.response.UserResponse;
import com.example.HospitalSystem.entity.usersAndRole.Users;
import com.example.HospitalSystem.exception.AppException;
import com.example.HospitalSystem.exception.ErrorCode;
import com.example.HospitalSystem.mapper.UserMapper;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

//    Search by patients'username
    @Override
    @PreAuthorize()
    public UserResponse getUserByUsername(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.UserToUserResponse(user);
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        String userName = userRequest.getUsername();
        String email = userRequest.getEmail();
        if(userRepository.existsByUsername(userName)){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByEmail(email)){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        Users user = userMapper.UserRequestToUsers(userRequest);
        return userMapper.UserToUserResponse(userRepository.save(user));
    }

//    Get My Info
    @PreAuthorize("isAuthenticated()")
    @Override
    public UserResponse getInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return getUserByUsername(username);
    }


    @PreAuthorize("isAuthenticated()")
    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Users userUpdated= userMapper.UserRequestToUsers(userRequest);
        return userMapper.UserToUserResponse(userRepository.save(userUpdated));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log("Sucessfull");
    }

}
