package com.example.market.report;


import com.example.market.entity.Report;
import com.example.market.entity.User;
import com.example.market.product.repository.ProductRepository;
import com.example.market.report.model.GetReportReq;
import com.example.market.report.model.GetReportRes;
import com.example.market.report.model.PostReport;
import com.example.market.report.model.ReportDto;
import com.example.market.report.repository.ReportRepository;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 전체 신고 조회 성공시 1 실패시 신고 조회 실패 리턴
    public Page<ReportDto> getAllReports(Pageable pageable) {
        try {
            Page<Report> reports = repository.findAll(pageable);
            return reports.map(report -> {
                ReportDto dto = new ReportDto();
                dto.setReportPk(report.getReportPk());
                dto.setReportedUserPk(report.getUserPk2()); // 신고당한 사용자 PK
                dto.setReportMessage(report.getReportMessage());
                dto.setReportType(report.getReportType());
                dto.setReportTarget(report.getReportTarget());
                dto.setCreatedAt(report.getCreatedAt());
                return dto;
            });
        } catch (Exception e) {
            System.out.println("신고 조회 실패: " + e.getMessage());
            return Page.empty(); // 실패 시 빈 페이지 반환
        }
    }

    private User getCurrentUser(MyUser myUser) {
        return userRepository.findById(myUser.getUserPk()).orElse(null);
    }
}

