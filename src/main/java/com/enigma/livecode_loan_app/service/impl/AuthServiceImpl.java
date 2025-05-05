package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.constant.ERole;
import com.enigma.livecode_loan_app.entity.AppUser;
import com.enigma.livecode_loan_app.model.request.AuthRequest;
import com.enigma.livecode_loan_app.model.response.LoginResponse;
import com.enigma.livecode_loan_app.model.response.RegisterResponse;
import com.enigma.livecode_loan_app.repository.AppUserRepository;
import com.enigma.livecode_loan_app.security.JwtTokenProvider;
import com.enigma.livecode_loan_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(AuthRequest request) {
        try {
            AppUser appUser = AppUser.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(ERole.ROLE_CUSTOMER)
                    .build();
            appUserRepository.saveAndFlush(appUser);
            return RegisterResponse.builder()
                    .id(appUser.getId())
                    .email(appUser.getEmail())
                    .role(appUser.getRole().getDescription())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL IS ALREADY REGISTERED");
        }
    }

    @Override
    public RegisterResponse createAdmin(AuthRequest request) {
        try {
            AppUser appUser = AppUser.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(ERole.ROLE_ADMIN)
                    .build();
            appUserRepository.saveAndFlush(appUser);
            return RegisterResponse.builder()
                    .email(appUser.getEmail())
                    .role(appUser.getRole().getDescription())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public RegisterResponse createStaff(AuthRequest request) {
        try {
            AppUser appUser = AppUser.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(ERole.ROLE_STAFF)
                    .build();
            appUserRepository.saveAndFlush(appUser);
            return RegisterResponse.builder()
                    .email(appUser.getEmail())
                    .role(appUser.getRole().getDescription())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL IS ALREADY REGISTERED");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getAuthorities().toString());

            return LoginResponse.builder()
                    .token(token)
                    .email(user.getUsername())
                    .role(user.getAuthorities().toString())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
