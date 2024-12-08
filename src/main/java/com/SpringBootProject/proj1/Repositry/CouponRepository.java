package com.SpringBootProject.proj1.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.proj1.Entitys.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long>{
    boolean existsByCode(String code);
    Optional<Coupon> findByCode(String code);
}
