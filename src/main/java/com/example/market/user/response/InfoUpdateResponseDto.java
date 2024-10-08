package com.example.market.user.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InfoUpdateResponseDto {

    private static long userPk;

    private InfoUpdateResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<InfoUpdateResponseDto> success(long userPk) {
        InfoUpdateResponseDto result = new InfoUpdateResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<InfoUpdateResponseDto> failure(String s) {
        InfoUpdateResponseDto result = new InfoUpdateResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
