package com.example.market.user;

import com.example.market.user.request.*;
import com.example.market.user.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userservice;

    @PostMapping("/sign-up")//회원가입
    public ResponseEntity<? super SignUpResponseDto> signUpUser(@RequestBody SignUpRequestDto dto) {
        return userservice.signUpUser(dto);
    }

    @PostMapping("/sign-in")//유저로그인
    public ResponseEntity<? super SignInResponseDto> signInUser(@RequestBody SignInRequestDto dto) {
        return userservice.signInUser(dto);
    }

    @PostMapping("/social-in")//소셜로그인
    public ResponseEntity<? super SocialResponseDto> socialIn(@RequestBody SocialRequestDto dto) {
        return userservice.socialIn(dto);
    }

    @PostMapping("/find")//아이디 및 비번 찾기
    public ResponseEntity<? super FindResponseDto> findId(@RequestBody FindRequestDto dto) {
        return userservice.findId(dto);
    }

    @GetMapping("/mail")//메일 인증
    public ResponseEntity<? super MailResponseDto> findMail(@RequestBody MailRequestDto dto) {
        return userservice.findMail(dto);
    }

    @PostMapping("/info")//마이페이지
    public ResponseEntity<? super InfoResponseDto> infoPage(@RequestBody InfoRequestDto dto) {
        return userservice.infoPage(dto);
    }

    @PatchMapping("/info-update")//마이페이지 수정
    public ResponseEntity<? super InfoUpdateResponseDto> infoUpdate(@RequestBody InfoUpdateRequestDto dto) {
        return userservice.infoUpdate(dto);
    }
    @PostMapping("/logout")//로그아웃
    public ResponseEntity<? super LogoutResponseDto> logout(@RequestBody LogoutRequestDto dto) {
        return userservice.logout(dto);
    }


}
