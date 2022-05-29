package com.boraozdogan.kekikpanel.api.dto;

import javax.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,
        boolean isAdmin) {
}
