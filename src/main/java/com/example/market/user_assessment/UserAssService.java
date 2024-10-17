package com.example.market.user_assessment;

import com.example.market.entity.User;
import com.example.market.entity.UserAssessment;
import com.example.market.manner.repository.MannerRepository;
import com.example.market.security.AuthenticationFacade;
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
    private final UserAssessmentRepository userAssessmentRepository;
    private final MannerRepository mannerRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

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
    }

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
