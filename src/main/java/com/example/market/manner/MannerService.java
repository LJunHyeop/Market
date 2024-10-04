package com.example.market.manner;

import com.example.market.entity.Manner;
import com.example.market.manner.common.MannerRes;
import com.example.market.manner.repository.MannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MannerService {
//    private final UserRepository userRepository;
    private final MannerRepository mannerRepository;

    public List<MannerRes> readManner(){
        List<Manner> mannerList=mannerRepository.findAll();
        List<MannerRes> answer=new ArrayList<>();
        for(Manner manner:mannerList){
            MannerRes res=new MannerRes();
            manner.getMannerId();
            manner.getSentence();
            manner.getType();
            answer.add(res);
        }
        return answer;
    }
}
