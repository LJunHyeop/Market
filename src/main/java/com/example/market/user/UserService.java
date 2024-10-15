package com.example.market.user;

import com.example.market.user.request.*;
import com.example.market.user.response.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //회원가입
    ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto);

    //유저로그인
    ResponseEntity<? super SignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto);

    //아이디 및 비번 찾기
    ResponseEntity<? super FindResponseDto> findId(FindRequestDto dto);

    //    //소셜로그인
    //    @Override
    //    public ResponseEntity<? super SocialResponseDto> socialIn(SocialRequestDto dto) {
    //        return null;
    //    }
    //
    //    //아이디 및 비번 찾기
    //    @Override
    //    public ResponseEntity<? super FindResponseDto> findId(FindRequestDto dto) {
    //        return null;
    //    }
    //
    //    //메일 인증
    //    @Override
    //    public ResponseEntity<? super MailResponseDto> findMail(MailRequestDto dto) {
    //        return null;
    //    }
    //
        //마이페이지
    ResponseEntity<? super InfoResponseDto> infoPage(InfoRequestDto dto);

    //마이페이지 수정
    ResponseEntity<? super InfoUpdateResponseDto> infoUpdate(InfoUpdateRequestDto dto);
}
