package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnums {

    AUTHOR_EXIST(110001, "作者已存在"),
    AUTHOR_HAS_BOOK(110002, "存在该作者的图书，无法删除"),

    PUBLISHER_EXIST(120001, "出版社已存在"),
    PUBLISHER_HAS_BOOK(120002, "存在该出版社出版图书，无法删除"),

    BOOK_EXIST(130001, "图书已存在"),

    USER_EXIST(140001, "用户已存在"),

    ROLE_EXIST(150001, "角色已存在"),

    USER_ROLE_EXIST(160001, "用户角色关系已存在"),

    PERMISSION_EXIST(170001, "权限已存在"),
    PERMISSION_HAS_ROLE(170002, "存在该权限的角色关联，无法删除"),

    LOGIN_USER_NOT_EXIST(370001, "用户不存在"),
    LOGIN_PASSWORD_ERROR(370002, "密码错误"),
    LOGIN_USER_NOT_LOGIN(470003, "用户未登录"),
    LOGIN_REGISTER_USER_EXIST(270004, "注册用户已存在"),

    LEND_USER_LEND_BOOK_EXIST(380001, "用户已借阅该图书且未归还"),
    LEND_RENEW_OVER_MAX_COUNT(380002, "续借次数超过最大值"),
    LEND_USER_NOT_LEND(280003, "用户未借阅该图书"),

    UTIL_PASSWORD_MD5_ERROR(490001, "密码MD5值异常");

    // 首位1表示管理端 2表示用户端 3表示双端共用 4表示非Service层代码异常
    // 次位均表示模块异常
    private final Integer code;
    private final String msg;
}
