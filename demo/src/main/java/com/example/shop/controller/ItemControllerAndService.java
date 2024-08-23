package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shop.repository.*;
import com.example.shop.model.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemControllerAndService {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }
    
    @PostMapping("/bulk")
    public ResponseEntity<List<Item>> addItems(@RequestBody List<Item> items) {
        List<Item> savedItems = itemRepository.saveAll(items);
        return new ResponseEntity<>(savedItems, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
    }

    @GetMapping
    public List<Item> listItems() {
        return itemRepository.findAll();
    }
}
