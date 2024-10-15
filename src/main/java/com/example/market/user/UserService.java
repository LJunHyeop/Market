package com.example.market.user;

import com.example.market.user.request.InfoRequestDto;
import com.example.market.user.request.InfoUpdateRequestDto;
import com.example.market.user.request.SignInRequestDto;
import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.InfoResponseDto;
import com.example.market.user.response.InfoUpdateResponseDto;
import com.example.market.user.response.SignInResponseDto;
import com.example.market.user.response.SignUpResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //회원가입
    ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto);

    //유저로그인
    ResponseEntity<? super SignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto);

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
