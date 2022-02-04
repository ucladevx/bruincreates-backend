package com.bruincreates.server.controller;

import com.bruincreates.server.model.request.SearchRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.model.response.SearchResponse;
import com.bruincreates.server.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping("/artwork")
    public RestResponse<SearchResponse> searchItem(@Valid @RequestBody SearchRequest request) {
        //TODO: implementation needed
        //TODO: pre-process keywords. e.g. "asa-sasa asa sasa" = "asa sasa"
        //TODO: search priority. e.g. category is a must match, keyword is a fuzzy search
        //TODO: result priority. how should you sort your match result if two results have the same ES score
        //TODO: pagination and how to indicate no more data available
        //TODO: should you return the entire product to frontend? how about just product id and product url?
        SearchResponse response = null;
        return RestResponse.success(response);
    }

    @PostMapping("/service")
    public RestResponse<SearchResponse> searchService(@Valid @RequestBody SearchRequest request) {
        //TODO: implementation needed
        //TODO: pre-process keywords. e.g. "asa-sasa asa sasa" = "asa sasa"
        //TODO: search priority. e.g. category is a must match, keyword is a fuzzy search
        //TODO: result priority. how should you sort your match result if two results have the same ES score
        //TODO: pagination and how to indicate no more data available
        //TODO: should you return the entire product to frontend? how about just product id and product url?
        SearchResponse response = null;
        return RestResponse.success(response);
    }

}
