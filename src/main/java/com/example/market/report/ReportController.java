package com.example.market.report;

import com.example.market.entity.Report;
import com.example.market.report.model.GetReportReq;
import com.example.market.report.model.GetReportRes;
import com.example.market.report.model.PostReport;
import com.example.market.report.model.ReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/report")
@Tag(name="신고", description="신고합니다. 지수현")
public class ReportController {

    private final ReportService service;



    //신고 하는 컨트롤러
    @PostMapping("/post")
    @Operation(summary = "신고하는 곳 ")
    public int postReport(@RequestBody PostReport p){
        return  service.postReport(p);
    }

    //신고 전체리스트 조회 컨트롤러
    @GetMapping("/Get/List")
    @Operation(summary = "전체 유저 신고 리스트")
    public Page<ReportDto> getReportList(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllReports(pageable);
    }
//    @GetMapping("/Get/User")
//    @Operation(summary = "특정유저 신고리스트")
//   public List<GetReportReq> getReportUser(@RequestParam GetReportRes p){
//        return service.GetReport(p);
//    }
}
