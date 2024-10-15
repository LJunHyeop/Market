package com.example.market.user;

import com.example.market.user.request.InfoRequestDto;
import com.example.market.user.request.InfoUpdateRequestDto;
import com.example.market.user.request.SignInRequestDto;
import com.example.market.user.request.SignUpRequestDto;
import com.example.market.user.response.InfoResponseDto;
import com.example.market.user.response.InfoUpdateResponseDto;
import com.example.market.user.response.SignInResponseDto;
import com.example.market.user.response.SignUpResponseDto;
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
@Tag(name="유저", description="유저 CRUD")
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
    public ResponseEntity<?super SignInResponseDto> signIn(HttpServletResponse res, @ParameterObject SignInRequestDto dto) {
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
}
