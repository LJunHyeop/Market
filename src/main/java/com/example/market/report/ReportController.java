package com.example.market.report;

import com.example.market.report.model.GetReportReq;
import com.example.market.report.model.GetReportRes;
import com.example.market.report.model.PostReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService service;


    //신고 하는 컨트롤러
    @PostMapping("/post")
    public int postReport(@RequestBody PostReport p){
        return  service.postReport(p);
    }
    //신고 전체리스트 조회 컨트롤러
    @GetMapping("/Get/List")
    public List<GetReportReq> getReportList(@RequestParam GetReportRes p){
        return  service.GetReportList(p);
    }
    @GetMapping("/Get/User")
    public List<GetReportReq> getReportUser(@RequestParam GetReportRes p){
        return service.GetReport(p);
    }
}
