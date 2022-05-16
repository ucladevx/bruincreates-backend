package com.bruincreates.server.controller;

import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.model.request.CreateProductRequest;
import com.bruincreates.server.model.request.DeleteProductRequest;
import com.bruincreates.server.model.request.UpdateProductRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.service.ProductService;
import com.bruincreates.server.utility.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public RestResponse<Product> createProduct(@Valid @RequestBody CreateProductRequest request) {
        String username = UserUtil.getRuntimeUser().getUsername();
        Product product = productService.createProduct(username, request);
        return RestResponse.success(product);
    }

    @PostMapping("/delete")
    public RestResponse<String> deleteProduct(@Valid @RequestBody DeleteProductRequest request) {
        String username = UserUtil.getRuntimeUser().getUsername();
        String productId = request.getProductId();
        productService.deleteProduct(username, productId);
        return RestResponse.success();
    }

    @PostMapping("/update")
    public RestResponse<Product> updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        String productId = request.getProductId();
        Product product = productService.updateProduct(productId, request);
        return RestResponse.success(product);
    }
}
