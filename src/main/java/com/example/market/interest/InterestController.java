package com.example.market.interest;

import com.example.market.interest.common.InterestRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
@Tag(name="관심상품", description="관심상품 확인")
public class InterestController {
    private final InterestService service;

    @PostMapping("/addition")
    @Operation(summary = "관심 상품 추가")
    public void postInterest(@RequestParam Long productPk){
        if(productPk==null){throw new RuntimeException();}
        service.postInterest(productPk);
    }

    @GetMapping("/check")
    @Operation(summary = "내 관심 상품 확인")
    public List<InterestRes> getMyInterestList(){
        return service.getMyInterestList();
    }

//    @PutMapping 있다 vs 없다

    @DeleteMapping("/deletion")
    @Operation(summary = "관심 상품 제거")
    public void deleteInterest(@RequestParam Long productPk){
        if(productPk==null){throw new RuntimeException();}
        service.deleteInterest(productPk);
    }

}
