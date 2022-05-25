package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteReviewRequest {

    @NotNull
    String username;

    @NotNull
    String productId;

}