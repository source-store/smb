package ru.smb.smb.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
//        return name();

        System.out.println("ROLE_" + name());

        return "ROLE_" + name();
    }
}