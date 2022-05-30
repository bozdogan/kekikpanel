package com.boraozdogan.kekikpanel.api.dto;

import javax.validation.constraints.NotBlank;

public class NoteBodyDTO {
    @NotBlank
    private final String body;

    public NoteBodyDTO(String body) {
        this.body = body;
    }

    public String body() {
        return body;
    }
}
