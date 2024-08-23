package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shop.model.*;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
