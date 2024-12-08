package com.SpringBootProject.proj1.Controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.SpringBootProject.proj1.dto.OrderDto;
import com.SpringBootProject.proj1.service.adminProduct.AdminPlaceOrder;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminOrderController {
    private final AdminPlaceOrder adminPlaceOrder;
    @GetMapping("/placeOrders")
    public ResponseEntity<List<OrderDto>> getAllPlaceOrders(){
        return ResponseEntity.ok(adminPlaceOrder.getAllPlaceOrders());
    }
    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
    OrderDto orderDto = adminPlaceOrder.changeOrderStatus(orderId, status);
    if (orderDto == null)
    return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
