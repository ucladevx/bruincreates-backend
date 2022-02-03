package com.bruincreates.server.controller;

import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @PostMapping("/artwork")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> recommendArtwork() {
        return RestResponse.success();
    }

    @PostMapping("/service")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> recommendService() {
        return RestResponse.success();
    }

}
