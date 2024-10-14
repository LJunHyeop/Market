package com.example.market.report.repository;

import com.example.market.entity.Report;
import com.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // User Pk를 사용하여 모든 Report를 반환하는 메소드 추가
    List<Report> findByUserPk2(Long userPk);
}
