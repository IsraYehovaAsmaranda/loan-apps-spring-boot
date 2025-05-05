package com.enigma.livecode_loan_app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private DecodedJWT getDecodedJwt(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJwt(token);
        return decodedJWT.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = getDecodedJwt(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getRoleFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJwt(token);
        String role = decodedJWT.getClaim("role").asString();

        if (role.startsWith("[") && role.endsWith("]")) {
            role = role.substring(1, role.length() - 1);
        }

        return role;
    }

    public String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        return header.substring(7);
    }
}
