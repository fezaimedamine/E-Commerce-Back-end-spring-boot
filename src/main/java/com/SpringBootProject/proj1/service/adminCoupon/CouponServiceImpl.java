package com.SpringBootProject.proj1.service.adminCoupon;

import java.util.List;

import org.springframework.stereotype.Service;

import com.SpringBootProject.proj1.Entitys.Coupon;
import com.SpringBootProject.proj1.Exception.ValidationException;
import com.SpringBootProject.proj1.Repositry.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{
    private final CouponRepository couponRepository;
    public Coupon cretaCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exists");
        }
        return couponRepository.save(coupon);
    }
    public List<Coupon> getAllCoupon(){
        return couponRepository.findAll();
    }
}
