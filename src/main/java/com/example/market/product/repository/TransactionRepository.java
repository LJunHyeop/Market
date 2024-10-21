package com.example.market.product.repository;

import com.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<com.example.market.entity.Transaction, Long> {
    List<Long> findProductByUser(User user);
}
