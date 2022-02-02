package com.bruincreates.server.model.response;

import com.bruincreates.server.dao.po.Product;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    List<Product> products;
    int hits;
}
