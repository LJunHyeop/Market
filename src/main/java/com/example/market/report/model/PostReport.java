package com.example.market.report.model;

import lombok.Data;

@Data

public class PostReport {
    private int reportPk;

    private String reportMessage;

    private int reportTarget;

    private int reportType;

}
