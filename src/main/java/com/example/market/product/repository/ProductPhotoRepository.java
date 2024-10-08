package com.example.market.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;

import java.util.List;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
    @Query("SELECT p FROM ProductPhoto p WHERE p.product = :product")
    List<ProductPhoto> findAllByProduct(Product product) ;
}
