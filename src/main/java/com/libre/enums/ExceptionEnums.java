package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnums {
    AUTHOR_EXIST(10101, "作者已存在"),
    AUTHOR_HAS_BOOK(10102, "存在该作者的图书，无法删除"),

    BOOK_EXIST(10201, "图书已存在"),

    MODULE_EXIST(10301, "模块已存在"),

    PERMISSION_EXIST(10401, "权限已存在"),
    PERMISSION_HAS_ROLE(10402, "存在该权限的角色关联，无法删除"),
    PERMISSION_DENIED(10403, "权限不足"),

    LEND_USER_NOT_LEND(10501, "用户未借阅该图书"),
    LEND_USER_LEND_BOOK_EXIST(10502, "用户已借阅该图书且未归还"),
    LEND_BOOK_EMPTY(10503, "借阅书籍库存为空"),
    LEND_RENEW_OVER_MAX_COUNT(10504, "续借次数超过最大值"),
    LEND_BOOK_NOT_EXIST(10505, "借阅书籍不存在"),

    LOGIN_USER_NOT_LOGIN(10601, "用户未登录"),
    LOGIN_USER_NOT_EXIST(10602, "用户不存在"),
    LOGIN_PASSWORD_ERROR(10603, "密码错误"),
    LOGIN_REGISTER_USER_EXIST(10604, "注册用户已存在"),

    PUBLISHER_EXIST(10701, "出版社已存在"),
    PUBLISHER_HAS_BOOK(10702, "存在该出版社出版图书，无法删除"),

    USER_EXIST(10801, "用户已存在"),

    ROLE_EXIST(10901, "角色已存在"),

    USER_ROLE_EXIST(11001, "用户角色关系已存在"),

    UTIL_PASSWORD_MD5_ERROR(11101, "密码MD5值异常");

    private final Integer code;
    private final String msg;
}
