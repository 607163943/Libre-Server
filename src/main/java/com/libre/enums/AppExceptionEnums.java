package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppExceptionEnums {
    ADMIN_PERMISSION_DENIED(10001, "权限不够，无法访问管理端");
    private final Integer code;
    private final String msg;
}
