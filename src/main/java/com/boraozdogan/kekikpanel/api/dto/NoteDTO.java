package com.boraozdogan.kekikpanel.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record NoteDTO(
        @NotNull
        String owner,
        @NotEmpty
        String body) {
}
