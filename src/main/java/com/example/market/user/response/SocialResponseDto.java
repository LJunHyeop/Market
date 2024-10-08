package com.example.market.user.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SocialResponseDto {

    private static long userPk;

    private SocialResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<SocialResponseDto> success(long userPk) {
        SocialResponseDto result = new SocialResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<SocialResponseDto> failure(String s) {
        SocialResponseDto result = new SocialResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
