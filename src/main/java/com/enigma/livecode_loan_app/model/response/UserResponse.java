package com.enigma.livecode_loan_app.model.response;

import com.enigma.livecode_loan_app.entity.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private List<Role> roles;
}
