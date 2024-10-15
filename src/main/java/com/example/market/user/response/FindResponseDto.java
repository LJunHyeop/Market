package com.example.market.user.response;

import com.example.market.user.request.FindRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.market.user.config.GlobalConst.SUCCESS_CODE;
import static com.example.market.user.config.GlobalConst.SUCCESS_MESSAGE;

@Setter
@Getter
public class FindResponseDto extends ResponseDto{
    private String userPw;

    private FindResponseDto(String userPw) {
        super(SUCCESS_CODE, SUCCESS_MESSAGE);
        this.userPw = userPw;
    }

    public static ResponseEntity<FindResponseDto> success(String userPw) {
        FindResponseDto result = new FindResponseDto(userPw);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<FindResponseDto> fail(String userPw) {
        FindResponseDto result = new FindResponseDto(userPw);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<FindResponseDto> noUser(String userPw) {
        FindResponseDto result = new FindResponseDto(userPw);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}