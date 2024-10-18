package com.example.market.manner;

import com.example.market.entity.Manner;
import com.example.market.entity.User;
import com.example.market.manner.common.MannerRes;
import com.example.market.manner.repository.MannerRepository;
import com.example.market.product.ProductController;
import com.example.market.product.repository.ProductRepository;
import com.example.market.product.repository.TransactionRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MannerService {
    private final MannerRepository mannerRepository;


    // 평가 창에 표시 될 매너 항목들
    public List<MannerRes> readManner(){
        List<Manner> mannerList=mannerRepository.findAll();
        List<MannerRes> answer=new ArrayList<>();
        for(Manner manner:mannerList){
            MannerRes res=new MannerRes();
            res.setMannerId(manner.getMannerId());
            res.setSentence(manner.getSentence());
            res.setType(manner.getType());
            answer.add(res);
        }
        return answer;
    }
}
