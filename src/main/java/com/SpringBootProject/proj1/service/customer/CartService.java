package com.SpringBootProject.proj1.service.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.SpringBootProject.proj1.dto.AddProductInCartDto;
import com.SpringBootProject.proj1.dto.OrderDto;
import com.SpringBootProject.proj1.dto.PlaceOrderDto;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getMyPlaceOrders(Long userId);
}
