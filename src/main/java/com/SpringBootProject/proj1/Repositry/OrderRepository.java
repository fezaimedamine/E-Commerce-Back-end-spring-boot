package com.SpringBootProject.proj1.Repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.enums.OrderStatus;
import com.SpringBootProject.proj1.Entitys.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
    List<Order> findAllByStatusIn(List<OrderStatus> orderStatusList);
    Order findByUserIdAndOrderStatus(Long userId,OrderStatus orderStatus);
    List<Order> findByUserIdAndOrderStatusIn(Long userId,List<OrderStatus> orderStatus);


}
