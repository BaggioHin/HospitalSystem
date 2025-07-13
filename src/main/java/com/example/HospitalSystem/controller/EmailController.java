package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.CheckCodeResponse;
import com.example.HospitalSystem.dto.response.SecretPasswordResponse;
import com.example.HospitalSystem.service.EmailService;
import com.example.HospitalSystem.service.UserService;
import com.example.HospitalSystem.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private EmailService emailService;


    @PostMapping("/send")
    public String sendEmail(@RequestParam String to
//                            @RequestParam String subject,@RequestParam String body
                            ) {
        emailServiceImpl.sendSimpleEmail(to);
        return "Email sent successfully!";
    }

//    quên mật khẩu
    @PostMapping("/password")
    public ApiResponse<SecretPasswordResponse> sendPassword(@RequestParam String email) {
        return ApiResponse.<SecretPasswordResponse>builder()
                .result(emailService.sendCode(email))
                .build();
    }

    @PostMapping("/checkcode")
    public ApiResponse<CheckCodeResponse> checkCode(@RequestParam String email,@RequestParam String code) {
        return ApiResponse.<CheckCodeResponse>builder()
                .result(emailService.CheckCode(email, code))
                .build();
    }
}
