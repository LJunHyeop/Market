package com.example.market.interest;

import com.example.market.entity.Interest;
import com.example.market.interest.common.InterestRes;
import com.example.market.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {
//    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    List<InterestRes> getMyInterestList(Long userPk){
        if(userPk==null){throw new RuntimeException();}
        List<Interest> list=interestRepository.findAllByUser(userPk);
        List<InterestRes> answer=new ArrayList<>();
        for(Interest interest:list){
            InterestRes res=new InterestRes();
            res.setInterestPk(interest.getInterestPk());
            res.setProductPk(interest.getProduct().getProductPk());
            res.setUserPk(interest.getUser().getUserPk());
        }
        return answer;
    }
}
