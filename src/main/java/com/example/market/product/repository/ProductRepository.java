package com.example.market.product.repository;

import java.util.List ;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;      // Page 클래스
import org.springframework.data.domain.Pageable; // Pageable 클래스
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.market.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    // @Query("SELECT p FROM product p WHERE p.productName = :p or p.productComment = :p")
    // List<Product> findProductsByProduct(String p) ;

    @Query("SELECT p FROM Product p WHERE p.productName = :p OR p.productComment = :p")
    Page<Product> findProductsByProduct(String p, Pageable pageable) ;

    long countByProductNameOrProductComment(String productName, String productComment);

    @Query("SELECT p.user.userPk FROM Product p WHERE p.productPk = :productPk")
    Long findUserPkByProductPk(@Param("productPk") Long productPk);
}
