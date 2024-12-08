package com.SpringBootProject.proj1.Controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootProject.proj1.Entitys.Coupon;
import com.SpringBootProject.proj1.Exception.ValidationException;
import com.SpringBootProject.proj1.service.adminCoupon.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        try{
            Coupon createCoupon=couponService.cretaCoupon(coupon);
            return ResponseEntity.ok(createCoupon);
        }catch(ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(couponService.getAllCoupon());
    }
    
}
