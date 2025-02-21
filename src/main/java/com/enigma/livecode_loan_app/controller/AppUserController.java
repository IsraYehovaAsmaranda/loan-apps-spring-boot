package com.enigma.livecode_loan_app.controller;

import com.enigma.livecode_loan_app.model.response.CommonResponse;
import com.enigma.livecode_loan_app.model.response.UserResponse;
import com.enigma.livecode_loan_app.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable String id) {
        UserResponse userResponse = appUserService.getById(id);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .message("SUCCESSFULLY GET USER BY ID")
                .data(userResponse)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
}
