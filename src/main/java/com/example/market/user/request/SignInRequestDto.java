package com.example.market.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequestDto {

    @Schema(defaultValue = "dkffkadl24@naver.com")
    private String userEmail;
    @Schema(defaultValue = "Test1234!@#$")
    private String userPw;
}
