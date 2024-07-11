package com.swmaestro.cotuber.domain.user;

public enum AuthRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;

    AuthRole(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
