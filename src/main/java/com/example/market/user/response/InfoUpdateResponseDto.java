package com.example.market.user.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.market.user.config.GlobalConst.SUCCESS_CODE;
import static com.example.market.user.config.GlobalConst.SUCCESS_MESSAGE;

@Getter
@Setter
public class InfoUpdateResponseDto extends ResponseDto{

    private String userName;

    private InfoUpdateResponseDto(String userName) {
        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userName = userName;
    }

    public static ResponseEntity<InfoUpdateResponseDto> success(String userName) {
        InfoUpdateResponseDto result = new InfoUpdateResponseDto(userName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
