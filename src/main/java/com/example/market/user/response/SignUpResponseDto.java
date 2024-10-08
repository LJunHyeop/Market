package com.example.market.user.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class SignUpResponseDto {

    private static long userPk;

    private SignUpResponseDto(long userPk) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPk = userPk;
    }

    public static ResponseEntity<SignUpResponseDto> success(long userPk) {
        SignUpResponseDto result = new SignUpResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<SignUpResponseDto> failure(String s) {
        SignUpResponseDto result = new SignUpResponseDto(userPk);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
