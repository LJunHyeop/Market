package com.example.market.user;

import com.example.market.user.request.*;
import com.example.market.user.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
@Tag(name="유저", description="유저")
public class UserController {
    private final UserService userservice;

    //  유저 페이지 - 회원기입  //
    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입", description = "USER_BOOK_DESCRIPTION")
    @ApiResponse(responseCode = "200", description = "USER_BOOK_RESPONSE_ERROR_CODE",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = SignUpResponseDto.class)))
    public ResponseEntity<?super SignUpResponseDto> signUp(@ParameterObject SignUpRequestDto dto) {
        return userservice.signUpUser(dto);
    }

    //  유저 페이지 - 로그인  //
    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "USER_BOOK_DESCRIPTION")
    @ApiResponse(responseCode = "200", description = "USER_BOOK_RESPONSE_ERROR_CODE",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = SignInResponseDto.class)))
    public ResponseEntity<?super SignInResponseDto> signIn(HttpServletResponse res, @RequestBody SignInRequestDto dto) {
        return userservice.signInUser(res, dto);
    }

    //  유저 페이지 - 마이페이지  //
    @GetMapping("/info")
    @Operation(summary = "마이페이지", description = "USER_BOOK_DESCRIPTION")
    @ApiResponse(responseCode = "200", description = "USER_BOOK_RESPONSE_ERROR_CODE",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = InfoResponseDto.class)))
    public ResponseEntity<?super InfoResponseDto> infoPage(@ParameterObject InfoRequestDto dto) {
        return userservice.infoPage(dto);
    }

    //  유저 페이지 - 마이페이지 수정  //
    @PatchMapping("/info-update")
    @Operation(summary = "마이페이지 수정", description = "USER_BOOK_DESCRIPTION")
    @ApiResponse(responseCode = "200", description = "USER_BOOK_RESPONSE_ERROR_CODE",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = InfoUpdateResponseDto.class)))
    public ResponseEntity<?super InfoUpdateResponseDto> info(@ParameterObject InfoUpdateRequestDto dto) {
        return userservice.infoUpdate(dto);
    }

    //  유저 페이지 - 비번 찾기 //
    @PostMapping("/find-pw")
    @Operation(summary = "비밀번호 찾기", description = "USER_BOOK_DESCRIPTION")
    @ApiResponse(responseCode = "200", description = "USER_BOOK_RESPONSE_ERROR_CODE",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = FindResponseDto.class)))
    public ResponseEntity<?super FindResponseDto> findId(@RequestBody FindRequestDto dto) {
        return userservice.findId(dto);
    }
    //  유저 페이지 - 아이디 찾기 //
    @PostMapping("/find-email")
    @Operation(summary = "이메일 찾기", description = "USER_BOOK_DESCRIPTION")
    @ApiResponse(responseCode = "200", description = "USER_BOOK_RESPONSE_ERROR_CODE",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = FindEmailResponseDto.class)))
    public ResponseEntity<?super FindEmailResponseDto> searchEmail(@RequestBody FindEmailRequestDto dto) {
        return userservice.searchEmail(dto);
    }

    @PostMapping("/sign-out")
    @Operation(summary = "로그아웃", description = "")
    @ApiResponse(responseCode = "200", description = "",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LogoutResponseDto.class)))
    public ResponseEntity<? super LogoutResponseDto> logout(HttpServletResponse res) {
        return userservice.logout(res);
    }
}
