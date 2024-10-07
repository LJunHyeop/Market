package com.example.market.report;


import com.example.market.entity.Report;
import com.example.market.report.model.GetReportReq;
import com.example.market.report.model.GetReportRes;
import com.example.market.report.model.PostReport;
import com.example.market.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private ReportMapper mapper;

    private ReportRepository repository;

    // 신고하는것 성공시 1 실패시 0 리턴
    public int postReport(PostReport p) {
        try {
            Report report = new Report();
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

     public List<GetReportReq> GetReport(GetReportRes p) {
        Report report = new Report();
        try{
            report.setUserPk(p.getUserPk());
            repository.findReportByUserPk(report.getUserPk());
        }catch (Exception e) {
            System.out.println("신고 조회가 없습니다.");
        }
        return repository.findReportByUserPk(report.getUserPk());
    }


    // 전체 신고 조회 성공시 1 실패시 신고 조회 실패 리턴
    public List<GetReportReq> GetReportList(GetReportRes p) {
        Report report = new Report();
        try{
            repository.findAllByReportPk(report);
        }catch (Exception e) {
            System.out.println("신고 조회 실패");
        }
        return repository.findAllByReportPk(report);
    }
}

