package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shop.model.*;

import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findByCartId(Long cartId);
}
