package com.example.market.user.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoUpdateRequestDto {
    @JsonIgnore
    private Long userPk;

    private String userName;

    private String userPhone;
}
