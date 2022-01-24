package com.bruincreates.server.controller;

import com.bruincreates.server.model.request.SearchRequest;
import com.bruincreates.server.model.response.RestResponse;
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

    @GetMapping("/test")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> test() {
        return RestResponse.success();
    }

    @PostMapping("/basic")
    public RestResponse<String> searchBasic(@Valid @RequestBody SearchRequest request) {
        //TODO: implementation needed
        //TODO: search basic doesn't require keywords or category
        return RestResponse.success();
    }

    @PostMapping("/item")
    public RestResponse<String> searchItem(@Valid @RequestBody SearchRequest request) {
        //TODO: implementation needed
        //TODO: search item can search with category or without category/filter
        return RestResponse.success();
    }

    @PostMapping("/service")
    public RestResponse<String> searchService(@Valid @RequestBody SearchRequest request) {
        //TODO: implementation needed
        //TODO: search service can search with category or without category/filter
        return RestResponse.success();
    }

}
