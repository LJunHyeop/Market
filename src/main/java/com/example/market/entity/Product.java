package com.example.market.entity;

import org.hibernate.annotations.* ;

import jakarta.persistence.* ;
import jakarta.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name="user_pk", nullable = false)
    @Comment("유저 FK")
    private User user ;

    @Column
    @Comment("상품 이름")
    private String productName ;

    @Column
    @Comment("상품 가격")
    private int productPrice ;

    @Column
    @Comment("좋아요")
    private int productLike ;
}
