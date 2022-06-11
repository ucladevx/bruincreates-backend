package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductReviewMapper;
import com.bruincreates.server.dao.po.ProductReview;
import com.bruincreates.server.dao.po.ProductReviewExample;
import com.bruincreates.server.model.request.CreateReviewRequest;
import com.bruincreates.server.model.request.DeleteReviewRequest;
import com.bruincreates.server.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductReviewService {

    @Autowired
    ProductReviewMapper productReviewMapper;

    @Autowired
    OrderService orderService;

    public void createReview (CreateReviewRequest request, String username) throws BadRequestException {

        String productId = request.getProductId();
        String reviewContent = request.getProductReview();

        // Validate reviewer has purchased product
        if(!orderService.hasCompletedOrder(username, productId)){
            throw new BadRequestException("user has not completed an order of this product");
        }

        // Validate reviewer did not previously review this product
        ProductReviewExample reviewExample = new ProductReviewExample();
        reviewExample.createCriteria()
                .andBuyerIdEqualTo(username)
                .andProductIdEqualTo(productId);
        List<ProductReview> reviews = productReviewMapper.selectByExample(reviewExample);

        int reviewsLength = reviews.size();
        if(reviewsLength == 1){
            throw new BadRequestException("user has already made a review for this product");
        }
        else if(reviewsLength > 1) {
            throw new BadRequestException("database needs fixing: there is more than 1 review in the db by this user for this product");
        }

        // create review object and insert into db
        ProductReview productReview = new ProductReview();
        productReview.setBuyerId(username);
        productReview.setProductId(productId);
        productReview.setProductReview(reviewContent);

        productReviewMapper.insertSelective(productReview);
    }

    public void deleteReview (DeleteReviewRequest request, String username) throws BadRequestException {

        String productId = request.getProductId();

        // Check if user is original buyer
        if(!orderService.hasCompletedOrder(username, productId)){
            throw new BadRequestException("user is not the original buyer of this product");
        }

        // Check review exists
        ProductReviewExample reviewExample = new ProductReviewExample();
        reviewExample.createCriteria()
                .andBuyerIdEqualTo(username)
                .andProductIdEqualTo(productId);
        List<ProductReview> reviews = productReviewMapper.selectByExample(reviewExample);

        int reviewsLength = reviews.size();
        if(reviewsLength == 0){
            throw new BadRequestException("user has has no existing reviews of this product");
        }
        else if(reviewsLength > 1) {
            throw new BadRequestException("database needs fixing: there is more than 1 review in the db by this user for this product");
        }

        // delete review object from mysql db
        productReviewMapper.deleteByExample(reviewExample);
    }

    public List<ProductReview> getProductReviews (String productId, int size, int offset) {

        // retrieve product reviews with same productId
        ProductReviewExample reviewExample = new ProductReviewExample();
        reviewExample.createCriteria().andProductIdEqualTo(productId);
        List<ProductReview> reviews = productReviewMapper.selectByExample(reviewExample);

        // add desired ProductReview objects to output array
        // based on size and offset
        int start = offset * size;
        List<ProductReview> reviewBatch = reviews.subList(start, start + size);

        return reviewBatch;
    }
}
