package com.boraozdogan.kekikpanel.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NoteDTO {
    @NotNull
    private final String owner;
    @NotEmpty
    private final String body;

    public NoteDTO(String owner, String body) {
        this.owner = owner;
        this.body = body;
    }

    public String owner() {
        return owner;
    }

    public String body() {
        return body;
    }
}
