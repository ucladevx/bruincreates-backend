package com.bruincreates.server.model.response;

import com.bruincreates.server.dao.po.Order;
import lombok.Data;

import java.util.List;

@Data
public class SellerOrderResponse {
    List<Order> pendingOrder;
    List<Order> shippedOrder;
    List<Order> pastOrder;
    List<Order> cancelledOrder;
}
