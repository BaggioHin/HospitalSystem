package com.example.HospitalSystem.configuration;

import com.example.HospitalSystem.exception.ErrorCode;
import com.example.HospitalSystem.service.impl.AuthenticationServiceImpl;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class AuthChannelInterceptor implements HandshakeInterceptor {
    @Autowired
    AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {

        // Lấy token từ query param hoặc header
        String token = null;
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            token = req.getParameter("token");
            if (token == null) token = req.getHeader("Authorization");
        }

        // Validate token (JWT)
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                SignedJWT signedJWT = authenticationServiceImpl.verifyToken(token, false);
                String username = signedJWT.getJWTClaimsSet().getSubject();
                attributes.put("username", username);
                return true;
            } catch (Exception e) {
                response.setStatusCode(ErrorCode.UNAUTHENTICATED.getStatusCode());
                return false;
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest req, ServerHttpResponse res,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
