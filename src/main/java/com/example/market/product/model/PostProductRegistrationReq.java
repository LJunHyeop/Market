package com.example.market.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PostProductRegistrationReq {
    @JsonIgnore
    private long productPk ;
    private long userPk ;
    private int productLike ;
    private String productName ;
    private int productPrice ;
    private String productComment ;
    private String createdAt ;
    private String updatedAt ;

    private String productPhoto ;
}
