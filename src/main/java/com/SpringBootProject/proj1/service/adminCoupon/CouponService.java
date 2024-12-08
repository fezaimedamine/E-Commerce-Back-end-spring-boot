package com.SpringBootProject.proj1.service.adminCoupon;

import java.util.List;

import com.SpringBootProject.proj1.Entitys.Coupon;

public interface CouponService {
    Coupon cretaCoupon(Coupon coupon);
    List<Coupon> getAllCoupon();
}
