package com.SpringBootProject.proj1.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.proj1.Entitys.CartItems;

@Repository
public interface CartItemsRepositry extends JpaRepository<CartItems,Long>{
    Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long productId,Long orderId,Long userId);
}
