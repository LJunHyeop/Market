package com.example.market.user_assessment;

import com.example.market.entity.Manner;
import com.example.market.entity.User;
import com.example.market.entity.UserAssessment;
import com.example.market.manner.MannerController;
import com.example.market.manner.repository.MannerRepository;
import com.example.market.user.repository.UserRepository;
import com.example.market.user_assessment.common.AssReq;
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
        UserAssessment userAssessment=new UserAssessment();
        // 평가를 받은 사용자
        User user=userRepository.getReferenceById(assReq.getUserPk());
        userAssessment.setUser(user);
        // 거래 직후 평가
        List<Long> reply=assReq.getReplyList();
/*
//      getMapping을 해서 유저에게 해당 평가가 있는지 확인
        List<Long> users=getUserManner();
        for(Long newAnswer:reply){
            for(Long oldAnswer:users){
                if(newAnswer==oldAnswer){
                    userAssessment.setCount(userAssessment.getCount()+1);
                }else{
                    Manner manner=mannerRepository.getReferenceById(newAnswer);
                    userAssessment.setManner(manner);
                }
            } // 수정 필요 불필요한 값이 들어감
        }
        */
    }

    // Post 내부 작업용
    public List<Long> getUserManner(){
        Long userPk = 0L;
//        userRepository.getReferenceById()
//        로그인한 유저의 전체 평가
        List<UserAssessment> allAss=userAssessmentRepository.findByUser_userPk(userPk);
        List<Long> pkList=new ArrayList<>();
        for(UserAssessment ass:allAss){
            Long pk=ass.getManner().getMannerId();
            pkList.add(pk);
        }
        return pkList;
    }
}
