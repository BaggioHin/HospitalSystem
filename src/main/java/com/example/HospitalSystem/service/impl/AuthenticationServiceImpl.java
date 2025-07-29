package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.dto.request.*;
import com.example.HospitalSystem.dto.response.AuthenticationResponse;
import com.example.HospitalSystem.dto.response.ChangePasswordResponse;
import com.example.HospitalSystem.dto.response.IntrospectResponse;
import com.example.HospitalSystem.entity.usersAndRole.invalidationTokenEntity;
import com.example.HospitalSystem.entity.usersAndRole.users;
import com.example.HospitalSystem.exception.AppException;
import com.example.HospitalSystem.exception.ErrorCode;
import com.example.HospitalSystem.repository.UserRepository;
import com.example.HospitalSystem.service.AuthenticationService;
import com.example.HospitalSystem.repository.InValidationTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InValidationTokenRepository invalidationTokenRepository;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .success(true)
                .build();
    }

    public String generateToken(users user) {
        JWSHeader header =new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet =new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("Baggio")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("SCOPE", buildScope(user))
//                .claim("specialties",)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));
            log.debug("Signer key bytes: {}", Arrays.toString(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        String token = introspectRequest.getToken();
        boolean isRefresh = true;
        try{
            verifyToken(token,isRefresh);
        }catch (AppException e){
            isRefresh = false;
        }
        return IntrospectResponse.builder().valid(isRefresh).build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException{
        try{
            SignedJWT signedJWT = verifyToken(logoutRequest.getToken(),false);

            String Jid = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            invalidationTokenEntity invalidationTokenEntity = com.example.HospitalSystem.entity.usersAndRole.invalidationTokenEntity.builder()
                    .id(Jid).expiryTime(expirationDate).build();

            invalidationTokenRepository.save(invalidationTokenEntity);
        }catch (AppException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(refreshTokenRequest.getToken(),true);
        String Jid = signedJWT.getJWTClaimsSet().getJWTID();
        String username = signedJWT.getJWTClaimsSet().getSubject();
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        if(invalidationTokenRepository.existsById(Jid)){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        invalidationTokenRepository.save(invalidationTokenEntity.builder()
                .id(Jid).expiryTime(expirationDate).build());

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .success(true)
                .token(token)
                .build();
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(changePasswordRequest.getToken(),true);
        String username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(user.getPassword(), changePasswordRequest.getOldPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        return ChangePasswordResponse.builder().success(true).build();
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes(StandardCharsets.UTF_8));
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (!isRefresh) ? signedJWT.getJWTClaimsSet().getExpirationTime()
                :new Date(signedJWT.getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli());

        var verified = signedJWT.verify(verifier);
        if(!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);
        if(invalidationTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }

    public String buildScope(users user) {
        StringBuilder builder = new StringBuilder();
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                builder.append("SCOPE_").append(role.getName());
                builder.append(" ");
            });
        }
        return builder.toString().trim();
    }

//    public String buildSpecialties(specialties specialties) {
//        StringBuilder builder = new StringBuilder();
//        if(!CollectionUtils.isEmpty(specialties.getName())){
//
//        }
//    }
}

