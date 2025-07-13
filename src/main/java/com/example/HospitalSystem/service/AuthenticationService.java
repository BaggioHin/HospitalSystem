package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.*;
import com.example.HospitalSystem.dto.response.AuthenticationResponse;
import com.example.HospitalSystem.dto.response.ChangePasswordResponse;
import com.example.HospitalSystem.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException;
    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) throws ParseException, JOSEException;
}
