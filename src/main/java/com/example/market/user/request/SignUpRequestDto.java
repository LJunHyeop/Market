package com.example.market.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String userName;
    private String userPhone;
    private String userPw;
    private String userEmail;

}
