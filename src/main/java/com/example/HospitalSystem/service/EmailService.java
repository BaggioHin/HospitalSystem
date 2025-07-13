package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.response.CheckCodeResponse;
import com.example.HospitalSystem.dto.response.SecretPasswordResponse;

public interface EmailService {
    SecretPasswordResponse sendCode(String username);
    CheckCodeResponse CheckCode(String username,String code);
}
