package com.example.market.user_assessment;

import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;
import com.example.market.entity.User;
import com.example.market.entity.UserAssessment;
import com.example.market.manner.repository.MannerRepository;
import com.example.market.product.repository.ProductPhotoRepository;
import com.example.market.product.repository.ProductRepository;
import com.example.market.product.repository.TransactionRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.user.repository.UserRepository;
import com.example.market.user_assessment.common.AssReq;
import com.example.market.user_assessment.common.AssRes;
import com.example.market.user_assessment.common.TransactionRes;
import com.example.market.user_assessment.repository.UserAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAssService {
    private final AuthenticationFacade authenticationFacade;

    private final UserAssessmentRepository userAssessmentRepository;
    private final MannerRepository mannerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final TransactionRepository transactionRepository;


    // 평가 가능 목록 조회 (a.k.a 거래 완료 리스트)
    public List<TransactionRes> readTradeHistory(){
        Long userPk=authenticationFacade.getLoginUserPk();
        User user=userRepository.findUserByUserPk(userPk);

        //리턴해줄 리스트
        List<TransactionRes> answer=new ArrayList();

        // 거래자가 나인 완료 거래 조회
        List<Long> transactionList=transactionRepository.findProductByUser(user);
        for(Long productPk:transactionList){
            // 각각의 상품의 정보 조회
            Product product=productRepository.getReferenceById(productPk);
            if(product.getProductStatus()!=2){ // 거래 완료가 아니면 지움
                return null;
            }
            // 판매자 이름, 물건, 사진 조회
            List<ProductPhoto> photos=productPhotoRepository.findAllByProduct(product);
            ProductPhoto thumbnail=photos.get(0);
            String partnerName=product.getUser().getUserName();
            String productName=product.getProductName();
            String photoName=thumbnail.getProductPhoto();

            // 리턴할 리스트 제조
            TransactionRes res=new TransactionRes();
            res.setProductPk(productPk);
            res.setPhotoName(photoName);
            res.setProductName(productName);
            res.setPartnerName(partnerName);
            answer.add(res);
        }

        return answer;
    }


    // 거래 상대 평가하기
    public void assUserManner(AssReq assReq) {
        Long userPk = assReq.getUserPk();
        List<Long> reply = assReq.getReplyList();
        User user = userRepository.getReferenceById(userPk); // 사용자 정보 조회

        for (Long mannerPk : reply) {
            UserAssessment userAssessment = new UserAssessment(); // 각 루프에서 새로운 객체 생성
            userAssessment.setUser(user);
            userAssessment.setManner(mannerRepository.getReferenceById(mannerPk));

            // 거래자가 체크한 항목을 이미 받은 적이 있는지 확인
            UserAssessment check = userAssessmentRepository.findByUser_userPkAndManner_MannerId(userPk, mannerPk);

            if (check == null) {
                // 해당 유저가 그 평가를 받은 적 없다면 새로 넣기
                userAssessment.setCount(1);
                userAssessmentRepository.save(userAssessment);
            }
                // 있으면 그 값에 1을 추가해서 update
                check.setCount(check.getCount() + 1); // 기존 객체 업데이트
                userAssessmentRepository.save(check); // 수정된 객체 저장
        }
        Product product=productRepository.getReferenceById(assReq.getProductPk());
        product.setProductStatus(3);
        productRepository.save(product);
    }

    // 나의 매너 평가 조회하기
    public List<AssRes> getMyManner(){
        Long userPk=authenticationFacade.getLoginUserPk();
        User user=userRepository.getReferenceById(userPk);
        List<UserAssessment> myAss=userAssessmentRepository.findByUser(user);
        List<AssRes> answer=new ArrayList<>();
        for(UserAssessment entity:myAss){
            AssRes res=new AssRes();
            res.setUserAssPk(entity.getUserAssPk());
            res.setMannerArticle(entity.getManner().getSentence());
            res.setCount(entity.getCount());
            answer.add(res);
        }
        return answer;
    }

    // 나의 매너 온도 계산
    public int getMannerScore(){
        Long userPk=authenticationFacade.getLoginUserPk();
        User user=userRepository.getReferenceById(userPk);
        List<UserAssessment> myAss=userAssessmentRepository.findByUser(user);
        int positive=0;
        int negative=0;
        for(UserAssessment entity:myAss){
            int mannerType=entity.getManner().getType();
            if(mannerType==1){
                positive+=entity.getCount();
            }
            negative+=entity.getCount();
        }
        int result=positive+(negative*-1);
        return result;
    }
}
