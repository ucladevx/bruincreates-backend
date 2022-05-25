package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductReviewMapper;
import com.bruincreates.server.dao.po.ProductReview;
import com.bruincreates.server.dao.po.ProductReviewExample;
import com.bruincreates.server.model.request.CreateReviewRequest;
import com.bruincreates.server.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewService {

    @Autowired
    ProductReviewMapper productReviewMapper;

    @Autowired
    OrderService orderService;

    public void createReview (CreateReviewRequest request) throws BadRequestException {

        String username = request.getUsername();
        String productId = request.getProductId();
        String reviewContent = request.getProductReview();

        // Validate reviewer has purchased product
        if(!orderService.hasCompletedOrder(username, productId)){
            throw new BadRequestException("user has not completed an order of this product");
        }

        // Validate reviewer did not previously review this product
        ProductReviewExample reviewExample = new ProductReviewExample();
        reviewExample.createCriteria().andBuyerIdEqualTo(username).andProductIdEqualTo(productId);
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
}
