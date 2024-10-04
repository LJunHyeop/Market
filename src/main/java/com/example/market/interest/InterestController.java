package com.example.market.interest;

import com.example.market.interest.common.InterestRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interest")
@RequiredArgsConstructor
public class InterestController {
    private final InterestService service;

    @PostMapping("addition")
    public void postInterest(@RequestParam Long productPk){
        if(productPk==null){throw new RuntimeException();}
        service.postInterest(productPk);
    }

    @GetMapping("/check")
    public List<InterestRes> getMyInterestList(){
        return service.getMyInterestList();
    }

//    @PutMapping 있다 vs 없다

//    상품 PK로(상품 페이지에서 눌렀다가 취소)
//    관심상품 PK번호로(목록 조회에서 삭제)
    @DeleteMapping("/deletion")
    public void deleteInterest(@RequestParam Long productPk){
        if(productPk==null){throw new RuntimeException();}
        service.deleteInterest(productPk);
    }

}
