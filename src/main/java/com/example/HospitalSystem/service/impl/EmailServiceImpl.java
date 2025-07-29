package com.example.HospitalSystem.service.impl;
import com.example.HospitalSystem.Utils.OtpGenerator;
import com.example.HospitalSystem.dto.response.CheckCodeResponse;
import com.example.HospitalSystem.dto.response.SecretPasswordResponse;
import com.example.HospitalSystem.entity.usersAndRole.emailEncoder;
import com.example.HospitalSystem.entity.usersAndRole.users;
import com.example.HospitalSystem.exception.AppException;
import com.example.HospitalSystem.exception.ErrorCode;
import com.example.HospitalSystem.repository.EmailEncoderRepository;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static java.time.LocalDate.*;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailEncoderRepository emailEncoderRepository;

    public void sendSimpleEmail(String to) {
        String otp = OtpGenerator.generateOtp(6);
        String body = "YOUR OTP IS "+ otp;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(to);
        message.setSubject("INFORMATION HOSPITAL SYSTEM");
        message.setText(body);
        mailSender.send(message);
    }

    public void simpleEmail(String to, String otp) {
        String body = "YOUR OTP IS "+ otp;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(to);
        message.setSubject("INFORMATION HOSPITAL SYSTEM");
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public SecretPasswordResponse sendCode(String email) {
        Optional<users> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Set<emailEncoder> emailEncoderList = optionalUser.get().getEmailEncoder();
        
        String otp = OtpGenerator.generateOtp(6);
        simpleEmail(email,otp);

        emailEncoder emailEncoder = new emailEncoder();
        emailEncoder.setUser(optionalUser.get());
        emailEncoder.setEncodedEmail(otp);
        emailEncoder.setExpiryDate(LocalDate.from(LocalDateTime.now().plusMinutes(1)));
        emailEncoderRepository.save(emailEncoder);

        return SecretPasswordResponse.builder()
//                .secretKey(OtpEncoder)
                .email(otp)
                .build();
    }

    @Override
    public CheckCodeResponse CheckCode(String email,String code) {
        var user = userRepository.findByEmail(email).get();
        Set<emailEncoder> emailEncoderList = user.getEmailEncoder();

        for(com.example.HospitalSystem.entity.usersAndRole.emailEncoder emailEncoder : emailEncoderList) {
            String codeEncoder=emailEncoder.getEncodedEmail();
            LocalDate expiryDate=emailEncoder.getExpiryDate();
            boolean status = emailEncoder.getStatus();
            if(codeEncoder.equals(code) && status==true
            && expiryDate.isAfter(now())) {
                emailEncoder.setStatus(false);
                emailEncoderRepository.save(emailEncoder);
                return CheckCodeResponse.builder()
                        .success(true).build();
            }
        }
        return CheckCodeResponse.builder().success(false).build();
    }

    @Scheduled(cron = "0 0 3 * * ?") // chạy mỗi ngày lúc 03:00
    public void cleanupExpiredEncoders() {
        int deletedCount = emailEncoderRepository.deleteByStatusTrueAndExpiryDateBefore(LocalDateTime.now().minusDays(1));
        System.out.println("Deleted " + deletedCount + " expired email encoders");
    }

    public void convertStatus(users user) {
        var emailEncodes = user.getEmailEncoder();
        for(com.example.HospitalSystem.entity.usersAndRole.emailEncoder emailEncoder : emailEncodes) {
            if(emailEncoder.getStatus()) {
                emailEncoder.setStatus(false);
                emailEncoderRepository.save(emailEncoder);
            }
        }
    }

    public void saveEncoder(String email){
        emailEncoder emailEncoder = new emailEncoder();
        emailEncoder.setEncodedEmail(email);
        emailEncoder.setExpiryDate(from(LocalDateTime.now().plusMinutes(5)));
        emailEncoder.setStatus(true);
        emailEncoderRepository.save(emailEncoder);
    }


}
