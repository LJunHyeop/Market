package com.example.market.user.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name(); //enum이 가지고 있는 메소드                   - 응답 코드
    HttpStatus getHttpStatus(); //httpStatus 멤버필드의 getter
    String getMessage(); //message 멤버필드의 getter           - 메시지
}
