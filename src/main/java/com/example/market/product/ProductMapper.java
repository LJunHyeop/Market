package com.example.market.product ;

import org.apache.ibatis.annotations.Mapper;

import com.example.market.product.model.UserFindRes ;

@Mapper
public interface ProductMapper {
    UserFindRes getUser(long userPk) ;
}