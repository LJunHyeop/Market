package com.example.market.entity;

import org.hibernate.annotations.* ;

import jakarta.persistence.* ;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product extends UpdateAt {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long productPk ;

    @ManyToOne
    @Column
    @Comment("유저 FK")
    private User userPk ;

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
