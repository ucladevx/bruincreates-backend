package com.bruincreates.server.controller;

import com.bruincreates.server.dao.mapper.OrderMapper;
import com.bruincreates.server.dao.po.Order;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.model.user.UserControlBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/test")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> test() throws InterruptedException{
        UserControlBlock user = (UserControlBlock) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUser().getUsername();
        String email = user.getUser().getEmail();

        Order order = new Order();
        order.setTransactionId("1");
        order.setBuyer("A");
        order.setSeller("J");
        order.setType((byte) 1);
        order.setStatus((byte) 1);
        order.setTotal(BigDecimal.valueOf(12.02));
        order.setShipping(BigDecimal.valueOf(30.02));
        orderMapper.insertSelective(order);
        return RestResponse.success();
    }

}
