package com.example.market.report.model;

import com.example.market.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class GetReportRes {

    @JsonIgnore
    private User userPk1;
    @JsonIgnore
    private long reportPk;

    private long userPk2;




}
