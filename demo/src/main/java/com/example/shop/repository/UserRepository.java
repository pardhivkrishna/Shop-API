package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shop.model.*;

public interface UserRepository extends JpaRepository<User, Long> {
}

