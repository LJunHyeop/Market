package com.example.market.report.model;

import com.example.market.entity.User;
import lombok.Data;

@Data
public class GetReportReq {
    private int reportPk;

    private String reportMessage;

    private int reportTarget;

    private int reportType;

    private User userPk;
}
