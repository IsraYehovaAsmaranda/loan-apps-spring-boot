package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.model.response.UserResponse;

public interface AppUserService {
    UserResponse getById(String id);
}
