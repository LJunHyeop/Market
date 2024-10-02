package com.example.market.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.market.entity.ProductPhoto;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
    
}
