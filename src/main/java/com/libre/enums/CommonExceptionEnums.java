package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonExceptionEnums {
    PERMISSION_DENIED(30101, "权限不够，无法访问管理端"),

    LOGIN_USER_NOT_LOGIN(30201, "用户未登录"),

    LEND_USER_LEND_BOOK_EXIST(30301, "用户已借阅该图书且未归还"),
    LEND_BOOK_EMPTY(30302, "借阅书籍库存为空"),
    LEND_RENEW_OVER_MAX_COUNT(30303, "续借次数超过最大值"),
    LEND_BOOK_NOT_EXIST(30304, "借阅书籍不存在"),

    PUBLISHER_EXIST(102001, "出版社已存在"),
    PUBLISHER_HAS_BOOK(102002, "存在该出版社出版图书，无法删除"),

    USER_EXIST(104001, "用户已存在"),

    ROLE_EXIST(105001, "角色已存在"),

    USER_ROLE_EXIST(106001, "用户角色关系已存在"),

    LOGIN_USER_NOT_EXIST(30101, "用户不存在"),
    LOGIN_PASSWORD_ERROR(30102, "密码错误"),
    LOGIN_REGISTER_USER_EXIST(30103, "注册用户已存在"),

    UTIL_PASSWORD_MD5_ERROR(410001, "密码MD5值异常");

    private final Integer code;
    private final String msg;
}
