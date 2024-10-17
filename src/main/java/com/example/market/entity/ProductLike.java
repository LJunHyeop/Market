package com.example.market.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.* ;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.* ; 

@Getter
@Setter
@Entity
@Table(
    name = "product_like",
    uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_pk", "product_pk"}
                )
        }
    )
public class ProductLike extends UpdateAt {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long productLikePk ;

    @JoinColumn(name = "user_pk", nullable = false)
    @Comment("유저 PK")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_pk", nullable = false)
    @Comment("상품 PK")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product ;
}
