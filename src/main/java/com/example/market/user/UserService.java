package com.example.market.user;

import com.example.market.user.request.SignInRequestDto;
import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.SignInResponseDto;
import com.example.market.user.response.SignUpResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //회원가입
    ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto);

    //유저로그인
    ResponseEntity<? super SignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto);
}
