package com.bruincreates.server.controller;

import com.bruincreates.server.dao.po.Order;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.CreateOrderRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.service.OrderService;
import com.bruincreates.server.utility.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<Order> create(@Valid @RequestBody CreateOrderRequest request) throws BadRequestException {
        String username = UserUtil.getRuntimeUser().getUsername();
        String productId = request.getProductId();
        Order order = orderService.createOrder(username, productId);
        return RestResponse.success(order);
    }
}
