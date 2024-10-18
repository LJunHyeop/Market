package com.example.market.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<com.example.market.entity.Transaction, Long> {

}
