package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchRequest {
    @NotNull
    int size;

    @NotNull
    int from;

    String keywords;
    List<String> categories;
}
