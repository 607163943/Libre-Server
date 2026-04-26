package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppExceptionEnums {
    LEND_USER_NOT_LEND(20101, "用户未借阅该图书");
    private final Integer code;
    private final String msg;
}
