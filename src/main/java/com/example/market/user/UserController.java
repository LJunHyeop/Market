package com.example.market.user;

import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.PostSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userservice;

    @PostMapping("/sign-up")//회원가입
    public ResponseEntity<? super PostSignUpResponseDto> signUpUser(@RequestBody SignUpRequestDto dto) {
        return userservice.signUpUser(dto);
    }
}
