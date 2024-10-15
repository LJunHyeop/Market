package com.example.market.user.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class FindRequestDto{

    private String userPhone;
    private String userEmail;
    private String userPw;


}
