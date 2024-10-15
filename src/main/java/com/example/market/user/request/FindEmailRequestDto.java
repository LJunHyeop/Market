package com.example.market.user.request;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class FindEmailRequestDto {
    private String userPhone;
    private String userName;
}
