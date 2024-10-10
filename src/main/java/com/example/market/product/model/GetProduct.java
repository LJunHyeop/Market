package com.example.market.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.market.entity.* ;

import lombok.Data;

@Data
public class GetProduct {
    @JsonIgnore
    private long productPk ;

    private String productName ;
    private String productComment ;
    private int productLiks ; 

    public GetProduct(Product product){
        this.productPk = product.getProductPk() ;
        this.productName = product.getProductName() ;
        this.productComment = product.getProductComment() ;
        this.productLiks = product.getProductLike() ;
    }
}
