package com.libre.constant;

public interface LendReviewState {
    // 待提交
    Integer SUBMIT = 0;
    // 待审核
    Integer WAIT = 1;
    // 审核通过
    Integer PASS = 2;
    // 审核未通过
    Integer NOT_PASS = 3;
}
