package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateProductRequest {
    @NotNull
    String title;
    @NotNull
    String description;
    @NotNull
    String type;
    @NotNull
    String keywords;
    @NotNull
    double price;
    @NotNull
    int stock;
    @NotNull
    List<String> images;
    @NotNull
    List<String> categories;
}
