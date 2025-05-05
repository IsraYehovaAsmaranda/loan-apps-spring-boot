package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.entity.AppUser;
import com.enigma.livecode_loan_app.model.response.UserResponse;
import com.enigma.livecode_loan_app.repository.AppUserRepository;
import com.enigma.livecode_loan_app.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserResponse getById(String id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND"));

        return UserResponse.builder()
                .email(appUser.getEmail())
                .role(appUser.getRole().getDescription())
                .build();
    }
}
