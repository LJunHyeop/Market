package com.example.market.user;

import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.PostSignUpResponseDto;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<?super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto);
}
