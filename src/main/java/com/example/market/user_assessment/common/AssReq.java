package com.example.market.user_assessment.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//    거래 후 사용자가 평가한 매너 항목
public class AssReq {
    // 어떤 사용자에게
    private Long UserPk;
    // 어떤 평가를
    private List<Long> replyList;
}
