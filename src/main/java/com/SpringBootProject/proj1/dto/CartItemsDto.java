package com.SpringBootProject.proj1.dto;

import lombok.Data;

@Data
public class CartItemsDto {
    private long id;
    private Long quantity ;

    private Long price;
    private Long productId;
    private Long orderId;
    private String productName;
    private byte[] returnedImg;
    private Long userId;
}
