package com.boraozdogan.kekikpanel.api.dto;

import javax.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    private final boolean isAdmin;

    public UserDTO(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
