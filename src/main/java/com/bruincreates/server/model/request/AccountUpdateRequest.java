package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateRequest {

    @NotNull
    String oldUsername;

    String newUsername;

    String newEmail;

    String newProfileName;
}
