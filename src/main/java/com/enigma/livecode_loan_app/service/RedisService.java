package com.enigma.livecode_loan_app.service;

public interface RedisService {
    void blacklistToken(String token, Long expirationTime);

    boolean isTokenBlacklisted(String token);
}
