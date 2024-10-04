package com.example.market.user;

import com.example.market.user.repository.UserRepository;
import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.PostSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override//회원가입
    public ResponseEntity<? super PostSignUpResponseDto> signUpUser(SignUpRequestDto dto) {
        dto.setUserName(dto.getUserName());
        dto.setUserPw(dto.getUserPw());
        dto.setUserPhone(dto.getUserPhone());



        return null;
    }
}
