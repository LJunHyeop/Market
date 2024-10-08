package com.example.market.user.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MailResponseDto {

    private static long userPk;

    private MailResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<MailResponseDto> success(long userPk) {
        MailResponseDto result = new MailResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<MailResponseDto> failure(String s) {
        MailResponseDto result = new MailResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
