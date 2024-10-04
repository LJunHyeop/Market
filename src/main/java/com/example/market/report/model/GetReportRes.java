package com.example.market.report.model;

import com.example.market.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class GetReportRes {

    private User userPk;

    private int reportPk;


}
