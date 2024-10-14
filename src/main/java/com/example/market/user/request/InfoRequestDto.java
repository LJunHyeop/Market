package com.example.market.user.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoRequestDto {
    @JsonIgnore
    private Long userPk;
    @JsonIgnore
    private String userEmail;
    @JsonIgnore
    private double userManner;
    @JsonIgnore
    private String userName;
    @JsonIgnore
    private String userPhone;

}
