package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateReviewRequest {

    @NotNull
    String productId;

    @NotNull
    String productReview;

}
