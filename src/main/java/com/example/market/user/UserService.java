package com.example.market.user;

import com.example.market.user.request.*;
import com.example.market.user.response.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    //회원가입
    ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto);

    //유저로그인
    ResponseEntity<? super SignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto);

    //    //소셜로그인
//    @Override
//    @Transactional
//    public ResponseEntity<? super SocialResponseDto> socialIn(SocialRequestDto dto) {
//        return null;
//    }
    // 이메일 찾기
    ResponseEntity<? super FindEmailResponseDto> searchEmail(FindEmailRequestDto dto);

    //아이디 및 비번 찾기
    ResponseEntity<? super FindResponseDto> findId(FindRequestDto dto);

    //마이페이지
    ResponseEntity<? super InfoResponseDto> infoPage(InfoRequestDto dto);

    //마이페이지 수정
    ResponseEntity<? super InfoUpdateResponseDto> infoUpdate(InfoUpdateRequestDto dto);

    //로그아웃
    ResponseEntity<? super LogoutResponseDto> logout(HttpServletResponse res);
}
