package com.example.market.user.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LogoutResponseDto {

    private static long userPk;

    private LogoutResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<LogoutResponseDto> success(long userPk) {
        LogoutResponseDto result = new LogoutResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<LogoutResponseDto> failure(String s) {
        LogoutResponseDto result = new LogoutResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
