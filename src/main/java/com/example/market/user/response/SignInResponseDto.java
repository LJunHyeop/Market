package com.example.market.user.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class SignInResponseDto {

    private String accessToken;

    private SignInResponseDto(String accessToken) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.accessToken = accessToken;
    }

    public static ResponseEntity<SignInResponseDto> success(String accessToken) {
        SignInResponseDto result = new SignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
