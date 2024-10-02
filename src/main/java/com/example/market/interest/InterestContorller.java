package com.example.market.interest;

import com.example.market.interest.common.InterestRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interest")
@RequiredArgsConstructor
public class InterestContorller {
    private final InterestService service;

    @PostMapping("addition")
    public void postInterest(@RequestParam Long productPk){
        if(productPk==null){ throw new RuntimeException();}

        // User user=userRepository.getReperenceById();
    }

    @GetMapping
    public List<InterestRes> getMyInterestList(Long userPk){
        return service.getMyInterestList(userPk);
    }

}
