package com.enigma.livecode_loan_app.controller;

import com.enigma.livecode_loan_app.model.request.AuthRequest;
import com.enigma.livecode_loan_app.model.response.CommonResponse;
import com.enigma.livecode_loan_app.model.response.LoginResponse;
import com.enigma.livecode_loan_app.model.response.RegisterResponse;
import com.enigma.livecode_loan_app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerUser(@Valid @RequestBody AuthRequest request) {
        RegisterResponse registerResponse = authService.register(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .message("Successfully registered")
                .data(registerResponse)
                .build();

        return ResponseEntity.created(URI.create("/api/auth/signup")).body(commonResponse);
    }

    @PostMapping(path = "/signup/staff")
    public ResponseEntity<CommonResponse<RegisterResponse>> createStaff(@Valid @RequestBody AuthRequest request) {
        RegisterResponse registerResponse = authService.createStaff(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .message("Successfully registered")
                .data(registerResponse)
                .build();

        return ResponseEntity.created(URI.create("/api/auth/signup")).body(commonResponse);
    }

    @PostMapping(path = "/signup/admin")
    public ResponseEntity<CommonResponse<RegisterResponse>> createAdmin(@Valid @RequestBody AuthRequest request) {
        RegisterResponse registerResponse = authService.createAdmin(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .message("Successfully registered")
                .data(registerResponse)
                .build();

        return ResponseEntity.created(URI.create("/api/auth/signup")).body(commonResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@Valid @RequestBody AuthRequest request) {
        LoginResponse loginResponse = authService.login(request);

        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .message("Successfully logged in")
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
}
