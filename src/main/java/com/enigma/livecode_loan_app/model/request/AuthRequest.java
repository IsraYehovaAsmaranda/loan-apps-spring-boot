package com.enigma.livecode_loan_app.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "Username is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
