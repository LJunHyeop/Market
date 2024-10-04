package com.example.market.repository;

import com.example.market.entity.Report;
import com.example.market.entity.User;
import com.example.market.report.model.GetReportReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<GetReportReq> findReportByUserPk(User user);

    List<GetReportReq>findAllBy(Report report);
}
