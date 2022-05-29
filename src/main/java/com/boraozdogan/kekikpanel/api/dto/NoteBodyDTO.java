package com.boraozdogan.kekikpanel.api.dto;

import javax.validation.constraints.NotBlank;

public record NoteBodyDTO(
        @NotBlank String body) {
}
