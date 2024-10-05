package com.example.market.user_assessment;

import com.example.market.user_assessment.common.AssReq;
import com.example.market.user_assessment.common.AssRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("assessment")
@RequiredArgsConstructor
public class UserAssController {
    private final UserAssService service;
    @PostMapping("deal")
    public void assUserManner(AssReq assReq){
        service.assUserManner(assReq);
    }
//    해당 유저에게 있는 평가 전부 호출(마이페이지)
    @GetMapping("total")
    public List<AssRes> getMyManner(){return service.getMyManner();}
}
