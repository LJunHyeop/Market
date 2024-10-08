package com.example.market.user;

import com.example.market.user.request.*;
import com.example.market.user.response.*;
import org.springframework.http.ResponseEntity;


public interface UserService {
    //회원가입
    ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto);
    //유저로그인
    ResponseEntity<?super SignInResponseDto> signInUser(SignInRequestDto dto);
    //소셜로그인
    ResponseEntity<?super SocialResponseDto> socialIn(SocialRequestDto dto);
    //아이디 및 비번 찾기
    ResponseEntity<?super FindResponseDto> findId(FindRequestDto dto);
    //메일 인증
    ResponseEntity<?super MailResponseDto> findMail(MailRequestDto dto);
    //마이페이지
    ResponseEntity<?super InfoResponseDto> infoPage(InfoRequestDto dto);
    //마이페이지 수정
    ResponseEntity<?super InfoUpdateResponseDto> infoUpdate(InfoUpdateRequestDto dto);
    //로그아웃
    ResponseEntity<?super LogoutResponseDto> logout(LogoutRequestDto dto);

}
