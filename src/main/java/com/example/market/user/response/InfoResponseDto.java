package com.example.market.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.market.user.config.GlobalConst.SUCCESS_CODE;
import static com.example.market.user.config.GlobalConst.SUCCESS_MESSAGE;

public class InfoResponseDto extends ResponseDto{
//    private String accessToken;
    private Long userPk;
    private String userEmail;
    private double userManner;
    private String userName;
    private String userPhone;




//    private InfoResponseDto(String accessToken) {
//        super(SUCCESS_CODE, SUCCESS_MESSAGE);
//        this.accessToken = accessToken;
//    }
//    public static ResponseEntity<InfoResponseDto> success(String accessToken) {
//        InfoResponseDto result = new InfoResponseDto(accessToken);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
        private InfoResponseDto(Long userPk, String userEmail, String userName, String userPhone, double userManner) {
        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userManner = userManner;
        this.userEmail = userEmail;
        this.userPk = userPk;
        this.userName = userName;
        this.userPhone = userPhone;
    }
    public static ResponseEntity<InfoResponseDto> success(Long userPk, String userEmail, String userName, String userPhone, double userManner) {
        InfoResponseDto result = new InfoResponseDto(userPk,userEmail, userName, userPhone, userManner);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
