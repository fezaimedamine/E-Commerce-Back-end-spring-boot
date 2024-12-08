package com.SpringBootProject.proj1.Controller.customer;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootProject.proj1.dto.AddProductInCartDto;
import com.SpringBootProject.proj1.dto.OrderDto;
import com.SpringBootProject.proj1.dto.PlaceOrderDto;
import com.SpringBootProject.proj1.service.customer.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto){
        return cartService.addProductToCart(addProductInCartDto);
    }


    @GetMapping("/cart{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId ){
        OrderDto orderDto=cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
    

    @GetMapping("coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId,@PathVariable String code){
        try{
            OrderDto orderDto=cartService.applyCoupon(userId, code);
            return ResponseEntity.ok(orderDto);
        }catch(ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCartDto));
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProductInCartDto));
    }
    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }
    @PostMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlaceOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlaceOrders(userId));
    }
}
