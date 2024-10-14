package com.example.market.report;


import com.example.market.entity.Report;
import com.example.market.entity.User;
import com.example.market.product.repository.ProductRepository;
import com.example.market.report.model.GetReportReq;
import com.example.market.report.model.GetReportRes;
import com.example.market.report.model.PostReport;
import com.example.market.report.repository.ReportRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final UserRepository userRepository;

    private final ReportRepository repository;

    private final AuthenticationFacade authenticationFacade;

    private final ProductRepository productRepository;
    // 신고하는것 성공시 1 실패시 0 리턴
    public int postReport(PostReport p) {
        try {
            //로그인한 유저정도
            User user = getCurrentUser(authenticationFacade.getLoginUser());

            Report report = new Report();
            report.setUserPk1(user);
            report.setUserPk2(productRepository.findUserPkByProductPk(p.getProductPk()));
            report.setReportTarget(p.getReportTarget());
            report.setReportType(p.getReportType());
            report.setReportMessage(p.getReportMessage());
            repository.save(report);
        }catch (Exception e) {
            return 0;
        }
        return 1;
    }
    // 특정 유저 신고 조회 신고조회 실패시 조회 없음
/*
public List<GetReportReq> GetReport(GetReportRes p) {
        Report report = new Report();
        try{
            report.setUserPk(p.getUserPk());
            repository.findReportByUserPk(report.getUserPk());
        }catch (Exception e) {
            System.out.println("신고 조회가 없습니다.");
        }
        return repository.findReportByUserPk(report.getUserPk());
    }*/



    // 전체 신고 조회 성공시 1 실패시 신고 조회 실패 리턴
    public List<Integer> getReportList(PostReport p) {
        // 신고당한 사람의 User PK를 가져옴
        Long userId = productRepository.findUserPkByProductPk(p.getProductPk()); // 가정: GetReportRes에서 userId를 가져온다고 가정
        try {
            // userId로 해당 사용자가 신고한 모든 Report를 조회
            List<Report> reports = repository.findByUserPk2(userId);

            // Report의 PK를 List<Long>으로 변환하여 반환
            List<Integer> reportPks = reports.stream()
                    .map(Report::getReportPk) // Report 객체에서 reportPk를 가져옴
                    .collect(Collectors.toList());
            return reportPks; // 리스트 반환
        } catch (Exception e) {
            System.out.println("신고 조회 실패: " + e.getMessage());
            return Collections.emptyList(); // 실패 시 빈 리스트 반환
        }
    }


    private User getCurrentUser(MyUser myUser) {
        return userRepository.findById(myUser.getUserPk()).orElse(null);
    }
}

