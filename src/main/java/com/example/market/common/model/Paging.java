package com.example.market.common.model;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Paging {
    private Integer size;
    private Integer page;
    @JsonIgnore
    private Integer startIdx;

    @ConstructorProperties({"page", "size"})
    public Paging(Integer page,Integer size ){
        this.page = page == null ||  page == 0 ? 1 : page ;
        this.size = size == null ||  size == 0 ? 10 : size ;
        this.startIdx = this.page -1  < 0 ? 0: (this.page - 1) * this.size ;
    
    }
}
