package com.example.market.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment ;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends UpdateAt {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long productPk ;

    @JoinColumn(name = "user_pk", nullable = false)
    @Comment("유저 PK")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    @Comment("상품 이름")
    private String productName ;

    @Column
    @Comment("상품 가격")
    private int productPrice ;

    @Column
    @Comment("좋아요")
    private int productLike ;

    @Column
    @Comment("상품 게시글")
    private String productComment ;
}
