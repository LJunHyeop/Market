package com.example.market.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 400
    EE(HttpStatus.BAD_REQUEST, "이메일이 비어있습니다."), // EMPTY_EMAIL
    DE(HttpStatus.BAD_REQUEST, "중복된 이메일 입니다."), // DUPLICATED_EMAIL
    IE(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."), // INVALID_EMAIL
    IPH(HttpStatus.BAD_REQUEST, "전화번호 형식이 올바르지 않습니다."), // INVALID_PHONE
    DN(HttpStatus.BAD_REQUEST, "중복된 닉네임 입니다."), // DUPLICATED_NICK_NAME
    DT(HttpStatus.BAD_REQUEST, "중복된 전화번호 입니다."), // DUPLICATED_TEL_NUMBER
    IP(HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다."), // INVALID_PASSWORD
    IN(HttpStatus.BAD_REQUEST, "닉네임 형식이 올바르지 않습니다."), // INVALID_NICKNAME
    NB(HttpStatus.BAD_REQUEST, "존재하지 않는 예약내역 입니다."), // NOT_EXISTED_BOOK
    NR(HttpStatus.BAD_REQUEST, "존재하지 않는 후기 입니다."), // NOT_EXISTED_REVIEW
    NU(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."), // NOT_EXISTED_USER
    VSP(HttpStatus.BAD_REQUEST, "별점 입력이 잘못되었습니다."), // VALIDATION_STAR_POINT
    RIE(HttpStatus.BAD_REQUEST, "이용 완료한 예약 정보를 불러오지 못했습니다."), // RESERVATION_ID_ERROR
    NMP(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."), // NOT_MATCH_PASSWORD

    NEP(HttpStatus.BAD_REQUEST, "승인된 유저는 처리할 수 없습니다.");



    private final HttpStatus httpStatus;
    private final String message;
}
