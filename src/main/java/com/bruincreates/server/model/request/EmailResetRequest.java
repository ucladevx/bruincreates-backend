package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailResetRequest {

    @NotNull
    String oldEmail;

    @NotNull
    String newEmail;

}
