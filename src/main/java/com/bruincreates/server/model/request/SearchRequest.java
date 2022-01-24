package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchRequest {
    @NotNull
    int size;
    @NotNull
    int from;
    String keywords;
    String category;
}
