package com.example.market.user.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InfoResponseDto {

    private static long userPk;

    private InfoResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<InfoResponseDto> success(long userPk) {
        InfoResponseDto result = new InfoResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<InfoResponseDto> failure(String s) {
        InfoResponseDto result = new InfoResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
