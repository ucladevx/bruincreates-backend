package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {

    @NotNull
    String username;

    @NotNull
    String password;

    @NotNull
    String email;

    @NotNull
    String profileName;

}
