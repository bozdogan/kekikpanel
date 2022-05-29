package com.boraozdogan.kekikpanel.api.dto;

public record UserRequestModel(
        String username,
        String password,
        boolean isAdmin) {
}
