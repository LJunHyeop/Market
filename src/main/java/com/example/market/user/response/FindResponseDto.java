package com.example.market.user.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FindResponseDto {

    private static long userPk;

    private FindResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<FindResponseDto> success(long userPk) {
        FindResponseDto result = new FindResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<FindResponseDto> failure(String s) {
        FindResponseDto result = new FindResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
