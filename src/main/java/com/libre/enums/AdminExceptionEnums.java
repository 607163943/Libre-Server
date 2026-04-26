package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminExceptionEnums {
    AUTHOR_EXIST(10101, "作者已存在"),
    AUTHOR_HAS_BOOK(10102, "存在该作者的图书，无法删除"),

    BOOK_EXIST(10201, "图书已存在"),

    MODULE_EXIST(10301, "模块已存在"),

    PERMISSION_EXIST(10401, "权限已存在"),
    PERMISSION_HAS_ROLE(10402, "存在该权限的角色关联，无法删除");
    private final Integer code;
    private final String msg;
}
