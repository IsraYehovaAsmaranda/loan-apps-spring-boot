package com.enigma.livecode_loan_app.constant;

import lombok.Getter;

@Getter
public enum ERole {
    ROLE_CUSTOMER("customer"),
    ROLE_STAFF("staff"),
    ROLE_ADMIN("admin");

    private final String description;

    ERole(String description) {
        this.description = description;
    }

    public static ERole findByDescription(String description) {
        for (ERole role : values()) {
            if (role.getDescription().equalsIgnoreCase(description)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role description: " + description);
    }
}
