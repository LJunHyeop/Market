package com.example.market.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.market.entity.*;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    @Query("SELECT ff FROM productLike ff WHERE ff.user.userPk = :userPk AND ff.product.productPk = :productPk")
    ProductLike findProductLikeByUserPkAndProductPk(long userPk, long ProductPk) ;
}
