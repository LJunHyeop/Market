package com.example.market.user_assessment;

import com.example.market.entity.User;
import com.example.market.entity.UserAssessment;
import com.example.market.manner.repository.MannerRepository;
import com.example.market.user.repository.UserRepository;
import com.example.market.user_assessment.common.AssReq;
import com.example.market.user_assessment.common.AssRes;
import com.example.market.user_assessment.repository.UserAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAssService {
    private UserAssessmentRepository userAssessmentRepository;
    private MannerRepository mannerRepository;
    private UserRepository userRepository;

    public void assUserManner(AssReq assReq){
        Long userPk=assReq.getUserPk();
        List<Long> reply=assReq.getReplyList();
        // 값을 저장할 사용자 정보
        UserAssessment userAssessment=new UserAssessment();
        User user=userRepository.getReferenceById(userPk);
        userAssessment.setUser(user);
        for(Long mannerPk:reply){
            userAssessment.setManner(mannerRepository.getReferenceById(mannerPk));
            // 거래자가 체크한 항목을 이미 받은 적이 있는지 확인
            UserAssessment check=userAssessmentRepository.findByUser_userPkAndManner_MannerId(userPk, mannerPk);
            if(check==null){
                // 해당 유저가 그 평가를 받은 적 없다면 새로 넣기
                userAssessment.setCount(1);
                userAssessmentRepository.save(userAssessment);
            }
            // 있으면 그 값에 1을 추가해서 update
            userAssessment.setCount(check.getCount()+1);
            userAssessmentRepository.save(userAssessment);
        }
    }
    public List<AssRes> getMyManner(){
        Long userPk=0L;
//                authenticationFacade.getLoginUserId();
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

    public int getMannerScore(){
        Long userPk=0L;
//                authenticationFacade.getLoginUserId();
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
