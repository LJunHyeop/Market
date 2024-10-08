package com.example.market.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 500
    DBE(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."), // DATABASE_ERROR

    // 400
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "입력이 잘못되었습니다."), // validation 어노테이션 에러
    INVALID_ENTITY(HttpStatus.BAD_REQUEST, "entity 정보를 불러올 수 없습니다. 입력된 값이 올바른지 확인 부탁드립니다."),
    MNF(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."), // MEMBER_NOT_FOUND
    EF(HttpStatus.BAD_REQUEST, "인증코드의 유효시간이 지났습니다."), // EXPIRED_CODE
    IC(HttpStatus.BAD_REQUEST, "인증코드가 올바르지 않습니다."), // INVALID_CODE
    VF(HttpStatus.BAD_REQUEST, "모든 정보를 입력해주세요."), // VALIDATION_FAILED
    NS(HttpStatus.BAD_REQUEST, "탈퇴한 회원 입니다."),
    WO(HttpStatus.BAD_REQUEST, "탈퇴 대기중인 회원 입니다."),
    WS(HttpStatus.BAD_REQUEST, "가입 대기중인 회원 입니다."),
    SF(HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다."), // SIGN_IN_FAILED

    // 403
    NP(HttpStatus.FORBIDDEN, "권한이 없습니다."); // NOT_PERMISSION

    private final HttpStatus httpStatus;
    private final String message;
}