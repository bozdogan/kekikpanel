package com.boraozdogan.kekikpanel.model;

public record UserRequestModel(
        String username,
        String password,
        boolean isAdmin) {
}
