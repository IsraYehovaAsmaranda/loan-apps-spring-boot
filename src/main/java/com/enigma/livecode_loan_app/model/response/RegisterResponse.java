package com.enigma.livecode_loan_app.model.response;

import com.enigma.livecode_loan_app.entity.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private String email;
    private List<Role> role;
}
