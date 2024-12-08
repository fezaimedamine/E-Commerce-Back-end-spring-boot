package com.SpringBootProject.proj1.dto;

import lombok.Data;

@Data
public class PlaceOrderDto {
    private Long userId;
    private String address;
    private String orderDescription;
    
}
