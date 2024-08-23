package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shop.model.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
