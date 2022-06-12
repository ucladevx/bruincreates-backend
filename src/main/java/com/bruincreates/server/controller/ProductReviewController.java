package com.bruincreates.server.controller;

import com.bruincreates.server.dao.po.ProductReview;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.DeleteReviewRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.model.request.CreateReviewRequest;
import com.bruincreates.server.service.ProductReviewService;
import com.bruincreates.server.utility.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/review")
public class ProductReviewController {

    @Autowired
    ProductReviewService productReviewService;

    @PostMapping("/create")
    public RestResponse<String> createReviews(@Valid @RequestBody CreateReviewRequest request) throws BadRequestException {
        String username = UserUtil.getRuntimeUser().getUsername();
        productReviewService.createReview(request, username);
        return RestResponse.success("Review posted");
    }

    @PostMapping("/delete")
    public RestResponse<String> deleteReview(@Valid @RequestBody DeleteReviewRequest request) throws BadRequestException {
        String username = UserUtil.getRuntimeUser().getUsername();
        productReviewService.deleteReview(request, username);
        return RestResponse.success("Review deleted");
    }

    @GetMapping("/get")
    public RestResponse<List<ProductReview>> getReviews(@RequestParam("product_id") String productId, @RequestParam("size") int size, @RequestParam("offset") int offset) {
        List<ProductReview> productReviews = productReviewService.getProductReviews(productId, size, offset);
        return RestResponse.success(productReviews);
    }
}
