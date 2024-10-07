package com.example.market.user_assessment;

import com.example.market.user_assessment.common.AssReq;
import com.example.market.user_assessment.common.AssRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment")
@RequiredArgsConstructor
@Tag(name="평가", description="평가 관련")
public class UserAssController {
    private final UserAssService service;
    @PostMapping("/deal")
    @Operation(summary = "거래 후 평가")
    public void assUserManner(@RequestBody AssReq assReq){
        service.assUserManner(assReq);
    }
//    해당 유저에게 있는 평가 전부 호출(마이페이지)
    @GetMapping("/total")
    @Operation(summary = "내가 받은 평가 확인")
    public List<AssRes> getMyManner(){return service.getMyManner();}

    @GetMapping("/logic")
    @Operation(summary = "매너 점수 반영")
    public int getMannerScore(){return service.getMannerScore();}
}
