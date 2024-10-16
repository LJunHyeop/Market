package com.example.market.report.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private int reportPk;

    private Long reportedUserPk; // 신고당한 사용자 PK

    private String reportedUserName; // 신고당한 사용자 닉네임

    private String reportMessage;

    private int reportType;

    private int reportTarget;

    private LocalDateTime createdAt;


}
