package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shop.repository.PaymentMethodRepository;
import com.example.shop.model.PaymentMethod;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @PostMapping
    public ResponseEntity<PaymentMethod> addPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return new ResponseEntity<>(savedPaymentMethod, HttpStatus.CREATED);
    }
}
