package com.example.market.user;

import com.example.market.entity.User;
import com.example.market.user.repository.UserRepository;
import com.example.market.user.request.*;
import com.example.market.user.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override//회원가입
    public ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto) {
        String username = dto.getUserName();
        String userPw = dto.getUserPw();
        String userPhone = dto.getUserPhone();

        boolean isExist = userRepository.existsByUserName(username);

        if (isExist){

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(SignUpResponseDto.failure("이미 존재하는 사용자입니다."));
        }

        User user  = new User();

        user.setUserName(username);
        user.setUserPw(bCryptPasswordEncoder.encode(userPw));
        user.setUserPhone(userPhone);

        User saverUser = userRepository.save(user);


        return SignUpResponseDto.success(saverUser.getUserPk());
    }

    //유저로그인
    @Override
    public ResponseEntity<? super SignInResponseDto> signInUser(SignInRequestDto dto) {
        return null;
    }

    //소셜로그인
    @Override
    public ResponseEntity<? super SocialResponseDto> socialIn(SocialRequestDto dto) {
        return null;
    }

    //아이디 및 비번 찾기
    @Override
    public ResponseEntity<? super FindResponseDto> findId(FindRequestDto dto) {
        return null;
    }

    //메일 인증
    @Override
    public ResponseEntity<? super MailResponseDto> findMail(MailRequestDto dto) {
        return null;
    }

    //마이페이지
    @Override
    public ResponseEntity<? super InfoResponseDto> infoPage(InfoRequestDto dto) {
        return null;
    }

    //마이페이지 수정
    @Override
    public ResponseEntity<? super InfoUpdateResponseDto> infoUpdate(InfoUpdateRequestDto dto) {
        return null;
    }

    //로그아웃
    @Override
    public ResponseEntity<? super LogoutResponseDto> logout(LogoutRequestDto dto) {
        return null;
    }
}
