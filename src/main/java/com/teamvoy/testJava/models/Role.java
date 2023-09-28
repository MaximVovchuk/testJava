package com.teamvoy.testJava.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MANAGER(1L, "manager"),
    CLIENT(2L, "client");
    private final Long code;
    private final String authority;

    Role(Long code, String authority) {
        this.code = code;
        this.authority = authority;
    }

    public Long getCode() {
        return code;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
