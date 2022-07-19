package com.bruincreates.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchRequest {
    String keywords;
}
