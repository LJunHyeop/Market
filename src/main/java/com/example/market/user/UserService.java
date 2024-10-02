package com.example.market.user;

import com.example.market.user.repository.UserRepository;
import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.PostSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface UserService {
    ResponseEntity<?super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto);
}
