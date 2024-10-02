package com.example.market.interest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("interest")
@RequiredArgsConstructor
public class InterestContorller {
    private final InterestService interestService;

    @PostMapping("addition")
    public void postInterest(@RequestParam Long productPk){
        if(productPk==null){ throw new RuntimeException();}

//        User user=userRepository.getReperenceById();
    }

}
