package com.example.market.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.market.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
