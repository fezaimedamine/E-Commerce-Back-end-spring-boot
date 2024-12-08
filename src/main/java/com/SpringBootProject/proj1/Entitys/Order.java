package com.SpringBootProject.proj1.Entitys;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

import com.SpringBootProject.enums.OrderStatus;
import com.SpringBootProject.proj1.dto.OrderDto;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;

    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id")
    private Coupon coupon;
    @OneToMany(fetch  =FetchType.LAZY, mappedBy="order")
    private List<CartItems> CartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUser(user.getName());
        if (coupon != null) {
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;
    }
}
