package com.example.market.manner;

import com.example.market.entity.Manner;
import com.example.market.entity.User;
import com.example.market.manner.common.MannerRes;
import com.example.market.manner.repository.MannerRepository;
import com.example.market.product.ProductController;
import com.example.market.product.repository.ProductRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MannerService {
    private final UserRepository userRepository;
    private final MannerRepository mannerRepository;
    private final ProductRepository productRepository;
//    private final TransactionRepository transactionRepository;
    private final AuthenticationFacade authenticationFacade;


    // 거래 리스트
    public List<Long> readTradeHistory(){
        Long userPk=authenticationFacade.getLoginUserPk();
        User user=userRepository.findUserByUserPk(userPk);

//        transactionRepository.findAllByUser(user);



        return null;
    }

    // 거래를 선택 했을 때 매너 평가
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
