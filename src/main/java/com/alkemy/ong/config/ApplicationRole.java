package com.alkemy.ong.config;

public enum ApplicationRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    ApplicationRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
