package com.enigma.livecode_loan_app.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private String id;
    private String email;
    private String role;
}
