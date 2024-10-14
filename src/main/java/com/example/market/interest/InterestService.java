package com.example.market.interest;

import com.example.market.entity.Interest;
import com.example.market.entity.Product;
import com.example.market.entity.User;
import com.example.market.interest.common.InterestRes;
import com.example.market.interest.repository.InterestRepository;
import com.example.market.product.repository.ProductRepository;

import com.example.market.user_assessment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final ProductRepository productRepository;

    // 관심 상품 추가
    public void postInterest(Long productPk){
        // 로그인한 유저 정보 받아오기
//        authenticationFacade.getLoginUserId()
        Long userId=0L;
        User user=userRepository.getReferenceById(userId);
        Interest interest=new Interest();
        interest.setUser(user);
        Product product=productRepository.getReferenceById(productPk);
        interest.setProduct(product);
        interestRepository.save(interest);
    }

    // 관심 상품 조회
    public List<InterestRes> getMyInterestList(){
//        authenticationFacade.getLoginUserId()
        Long userPk=0L; /* userRepository 값 대입 */
        User user=userRepository.getReferenceById(userPk);
//        customException?
        if(userPk==null){throw new RuntimeException();}
        List<Interest> list=interestRepository.findByUser(user);
        List<InterestRes> answer=new ArrayList<>();
        for(Interest interest:list){
            InterestRes res=new InterestRes();
            res.setInterestPk(interest.getUser().getUserPk());
            res.setProductPk(interest.getProduct().getProductPk());
            res.setUserPk(interest.getUser().getUserPk());
        }
        return answer;
    }

    // 관심 상품 삭제
    public void deleteInterest(Long productPk){
        Interest interest=new Interest();
//        User user=userRepository.getByUserId();
//        interest.setUser(user);
        Product product=productRepository.getReferenceById(productPk);
        interest.setProduct(product);
        interestRepository.delete(interest);
    }
}
