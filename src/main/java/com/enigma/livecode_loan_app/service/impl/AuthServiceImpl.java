package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.constant.ERole;
import com.enigma.livecode_loan_app.entity.AppUser;
import com.enigma.livecode_loan_app.entity.Role;
import com.enigma.livecode_loan_app.model.request.AuthRequest;
import com.enigma.livecode_loan_app.model.response.LoginResponse;
import com.enigma.livecode_loan_app.model.response.RegisterResponse;
import com.enigma.livecode_loan_app.repository.AppUserRepository;
import com.enigma.livecode_loan_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(AuthRequest request) {
        try {
            AppUser appUser = AppUser.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(null)
                    .build();
            appUserRepository.saveAndFlush(appUser);
            return RegisterResponse.builder()
                    .email(appUser.getEmail())
                    .role(appUser.getRoles())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL IS ALREADY REGISTERED");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        AppUser appUser = appUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WRONG USERNAME OR PASSWORD"));

        if(!passwordEncoder.matches(request.getPassword(), appUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WRONG USERNAME OR PASSWORD");
        }

        return LoginResponse.builder()
                .email(appUser.getEmail())
                .role(null)
                .token(null)
                .build();
    }
}
