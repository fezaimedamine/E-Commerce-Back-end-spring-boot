package com.SpringBootProject.proj1.service.adminProduct;

import java.util.List;

import com.SpringBootProject.proj1.dto.OrderDto;

public interface AdminPlaceOrder {
    List<OrderDto> getAllPlaceOrders();
    OrderDto changeOrderStatus (Long orderId, String status);

}
