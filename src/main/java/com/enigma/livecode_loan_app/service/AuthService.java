package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.model.request.AuthRequest;
import com.enigma.livecode_loan_app.model.response.LoginResponse;
import com.enigma.livecode_loan_app.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    RegisterResponse createAdmin(AuthRequest request);
    RegisterResponse createStaff(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
