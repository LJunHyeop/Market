package com.example.market.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}