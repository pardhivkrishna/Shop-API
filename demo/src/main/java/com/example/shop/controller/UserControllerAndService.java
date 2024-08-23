package com.example.shop.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shop.repository.*;
import com.example.shop.model.*;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/users")
public class UserControllerAndService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        
        System.out.println("Saved user: " + savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<User>> addUsers(@RequestBody List<User> users) {
        List<User> savedUsers = userRepository.saveAll(users);
        savedUsers.forEach(user -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        });
        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @PostMapping("/{userId}/payment-methods")
    public ResponseEntity<User> addPaymentMethodToUser(@PathVariable Long userId, @RequestParam Long paymentMethodId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found"));

        user.getPaymentMethods().add(paymentMethod);
        userRepository.save(user);  // This save operation should persist the relationship

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/payment-methods")
    public ResponseEntity<Set<PaymentMethod>> getUserPaymentMethods(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ResponseEntity<>(user.getPaymentMethods(), HttpStatus.OK);
    }
}
