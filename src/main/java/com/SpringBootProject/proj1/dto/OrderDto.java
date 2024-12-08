package com.SpringBootProject.proj1.dto;

import java.sql.Date;
import java.util.List;
import java.util.UUID;


import com.SpringBootProject.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;

    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    private String user;
    private List<CartItemsDto> CartItems;
    private String couponName;
}
