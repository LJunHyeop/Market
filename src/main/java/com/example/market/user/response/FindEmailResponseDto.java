package com.example.market.user.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.market.user.config.GlobalConst.SUCCESS_CODE;
import static com.example.market.user.config.GlobalConst.SUCCESS_MESSAGE;

@Setter
@Getter
public class FindEmailResponseDto extends ResponseDto{

    private String userEmail;
    private FindEmailResponseDto(String userEmail) {
        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userEmail = userEmail;

    }

    public static ResponseEntity<FindEmailResponseDto> success(String userEmail) {
        FindEmailResponseDto result = new FindEmailResponseDto(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
