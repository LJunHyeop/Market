package com.example.market.product.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.market.entity.*;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    @Query("SELECT ff FROM ProductLike ff WHERE ff.user.userPk = :userPk AND ff.product.productPk = :productPk")
    ProductLike findProductLikeByUserPkAndProductPk(@Param("userPk") long userPk, @Param("productPk") long productPk);
}
