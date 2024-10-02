package com.example.market.interest;

import com.example.market.interest.common.InterestRes;
import com.example.market.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {
//    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    List<InterestRes> getMyInterestList(Long UserPk){
        if(UserPk==null){throw new RuntimeException();}
        return null;
    }
}
