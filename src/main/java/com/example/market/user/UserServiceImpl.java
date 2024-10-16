package com.example.market.user;

import com.example.market.common.AppProperties;
import com.example.market.common.CookieUtils;
import com.example.market.entity.User;
import com.example.market.jwt.JwtTokenProvider;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.exception.CommonErrorCode;
import com.example.market.user.exception.CustomException;
import com.example.market.user.exception.UserErrorCode;
import com.example.market.user.repository.UserRepository;
import com.example.market.user.request.*;
import com.example.market.user.response.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;
    private final PasswordEncoder passwordEncoder;

    @Override//회원가입
    @Transactional
    public ResponseEntity<? super SignUpResponseDto> signUpUser(SignUpRequestDto dto) {

        String userEmail = dto.getUserEmail();
        String userPw = dto.getUserPw();
        String userPhone = dto.getUserPhone();

        boolean isExist = userRepository.existsByUserEmail(userEmail);

        if (isExist) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(SignUpResponseDto.failure("이미 존재하는 사용자입니다."));
        }

        User user = new User();

        user.setUserEmail(userEmail);
        user.setUserPw(bCryptPasswordEncoder.encode(userPw));
        user.setUserPhone(userPhone);
        user.setUserName(dto.getUserName());
        user.setUserType(1);
        user.setUserManner(36.5);
        user.setUserState(1);
        user.setUserSocial(1);
        user.setUserRole(1);


        User saverUser = userRepository.save(user);


        return SignUpResponseDto.success(saverUser.getUserPk());
    }

    //유저로그인
    @Override
    @Transactional
    public ResponseEntity<? super SignInResponseDto> signInUser(HttpServletResponse res, SignInRequestDto dto) {


        String accessToken = null;
        String refreshToken = null;
        try {

            if (dto.getUserEmail() == null || dto.getUserEmail().isEmpty()) {
                throw new CustomException(CommonErrorCode.VF);
            }
            if (dto.getUserPw() == null || dto.getUserPw().isEmpty()) {
                throw new CustomException(CommonErrorCode.VF);
            }

            String userEmail = dto.getUserEmail();
            User user = userRepository.findByUserEmail(userEmail);
            if (user == null) {
                throw new CustomException(CommonErrorCode.SF);
            }
            String userPw = dto.getUserPw();
            String encodingPw = user.getUserPw();
            boolean matches = bCryptPasswordEncoder.matches(userPw, encodingPw);
            if (!matches) {
                throw new CustomException(CommonErrorCode.SF);
            }
            MyUser myUser = MyUser.builder()
                    .userPk(user.getUserPk())
                    .role(user.getUserRole())
                    .build();

            accessToken = jwtTokenProvider.generateAccessToken(myUser);
            log.info("access token: {}", accessToken);
            refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

            //  RefreshToken 을 갱신한다.  //
            int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
            cookieUtils.deleteCookie(res, "refresh-token");
            cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CommonErrorCode.DBE);
        }
        return SignInResponseDto.success(accessToken);
    }

    //    //소셜로그인
//    @Override
//    @Transactional
//    public ResponseEntity<? super SocialResponseDto> socialIn(SocialRequestDto dto) {
//        return null;
//    }

    // 이메일 찾기
    @Override
    @Transactional
    public ResponseEntity<? super FindEmailResponseDto> searchEmail(FindEmailRequestDto dto) {

        try { if (dto == null) { throw new CustomException(CommonErrorCode.VF); }
        } catch (CustomException e) { throw new CustomException(e.getErrorCode()); }

        User user = userRepository.findByUserNameAndUserPhone(dto.getUserName(), dto.getUserPhone());

        try {
            if (user == null) { throw new CustomException(UserErrorCode.NU); }
        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CommonErrorCode.DBE);
        }

        return FindEmailResponseDto.success(user.getUserEmail());

    }

    //비번 찾기
    @Override
    @Transactional
    public ResponseEntity<? super FindResponseDto> findId(FindRequestDto dto) {

        try { if (dto == null) { throw new CustomException(CommonErrorCode.VF); }
        } catch (CustomException e) { throw new CustomException(e.getErrorCode()); }

        User user = userRepository.findByUserEmailAndUserPhone(dto.getUserEmail(), dto.getUserPhone());

        try {

            if (user == null) { throw new CustomException(UserErrorCode.NU); }

            String userPw = dto.getUserPw();
            String encodingPw = passwordEncoder.encode(userPw);
            dto.setUserPw(encodingPw);

            user.setUserPw(dto.getUserPw());

            userRepository.save(user);

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CommonErrorCode.DBE);
        }

        return FindResponseDto.success();

    }

    //    //메일 인증
//    @Override
//        @Transactional
//    public ResponseEntity<? super MailResponseDto> findMail(MailRequestDto dto) {
//        return null;
//    }
//
    //마이페이지
    @Override
    @Transactional
    public ResponseEntity<? super InfoResponseDto> infoPage(InfoRequestDto dto) {


        try {
            dto.setUserPk(authenticationFacade.getLoginUserPk());
            if (dto.getUserPk() <= 0) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CommonErrorCode.MNF);
        }

        User user = userRepository.findUserByUserPk(dto.getUserPk());
        dto.setUserPk(user.getUserPk());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserManner(user.getUserManner());
        dto.setUserName(user.getUserName());
        dto.setUserPhone(user.getUserPhone());
        return InfoResponseDto.success(user.getUserPk(), user.getUserEmail(), user.getUserName(), user.getUserPhone(), user.getUserManner());
    }

    //마이페이지 수정
    @Override
    @Transactional
    public ResponseEntity<? super InfoUpdateResponseDto> infoUpdate(InfoUpdateRequestDto dto) {
        try {
            dto.setUserPk(authenticationFacade.getLoginUserPk());
            if (dto.getUserPk() <= 0) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CommonErrorCode.MNF);
        }
        User user = userRepository.findUserByUserPk(dto.getUserPk());
        dto.setUserPk(user.getUserPk());
        user.setUserName(dto.getUserName());
        user.setUserPhone(dto.getUserPhone());
        userRepository.save(user);
        return InfoUpdateResponseDto.success(user.getUserName());
    }

    //로그아웃
    @Override
    @Transactional
    public ResponseEntity<? super LogoutResponseDto> logout(HttpServletResponse res) {
        cookieUtils.deleteCookie(res, "refresh-token");

        return LogoutResponseDto.success();
    }
}
