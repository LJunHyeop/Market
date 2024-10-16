package com.example.market.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.* ;
import lombok.Data;

@Data
@Entity
@Table(name = "product_photo")
public class ProductPhoto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long productPhotoPk ;

    @ManyToOne
    @JoinColumn(name="product_pk", nullable = false)
    @Comment("상품 PK")
    private Product product ;

    @Column
    @Comment("상품 사진")
    private String productPhoto ;
}
