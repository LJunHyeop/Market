package com.example.market.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequestDto {

    private String userEmail;

    private String userPw;
}
