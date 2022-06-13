package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsernameResetRequest {

    @NotNull
    String oldUsername;

    @NotNull
    String newUsername;

}
