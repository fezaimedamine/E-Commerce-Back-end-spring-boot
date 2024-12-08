package com.SpringBootProject.proj1.service.adminProduct;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.SpringBootProject.enums.OrderStatus;
import com.SpringBootProject.proj1.Entitys.Order;
import com.SpringBootProject.proj1.Repositry.OrderRepository;
import com.SpringBootProject.proj1.dto.OrderDto;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AdminPlaceOrderImpl implements AdminPlaceOrder{
    private final OrderRepository orderRepository;
    public List<OrderDto> getAllPlaceOrders(){
        List<Order> orderList=orderRepository.findAllByStatusIn(List.of(OrderStatus.Placed,OrderStatus.Shipped, OrderStatus.Delivered));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }
    public OrderDto changeOrderStatus (Long orderId, String status){
        java.util.Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
        Order order = optionalOrder.get();
        if(Objects.equals(status, "Shipped")){
        order.setOrderStatus (OrderStatus.Shipped);
        }else if(Objects.equals(status, "Delivered")) {
        order.setOrderStatus (OrderStatus.Delivered);
        }
        return orderRepository.save(order).getOrderDto();
        }
        return null;
        }
    
}
