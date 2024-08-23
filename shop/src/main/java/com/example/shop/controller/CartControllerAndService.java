package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shop.repository.*;
import com.example.shop.model.*;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/carts")
public class CartControllerAndService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found for user id " + userId);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestParam Long itemId, @RequestParam int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart); 
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        if (item.getQuantity() < quantity) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        item.setQuantity(item.getQuantity() - quantity);
        itemRepository.save(item); 

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }


    //curl -X POST "http://localhost:8080/1/items?itemId=1&quantity=3"

    @GetMapping("/{userId}/items")
    public ResponseEntity<Set<CartItem>> getItemsInCart(@PathVariable Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found for user id " + userId);
        }
        return new ResponseEntity<>(cartItemRepository.findByCartId(cart.getId()), HttpStatus.OK);
    }
}
