package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonExceptionEnums {
    // code格式说明
    // 首位表示使用范围 1表示管理端 2表示用户端 3表示双端共用 4表示非Service层代码异常
    // 第2和3位均表示模块异常
    // 其余则是异常序号

    AUTHOR_EXIST(101001, "作者已存在"),
    AUTHOR_HAS_BOOK(101002, "存在该作者的图书，无法删除"),

    PUBLISHER_EXIST(102001, "出版社已存在"),
    PUBLISHER_HAS_BOOK(102002, "存在该出版社出版图书，无法删除"),

    BOOK_EXIST(103001, "图书已存在"),

    USER_EXIST(104001, "用户已存在"),

    ROLE_EXIST(105001, "角色已存在"),

    USER_ROLE_EXIST(106001, "用户角色关系已存在"),

    PERMISSION_EXIST(107001, "权限已存在"),
    PERMISSION_HAS_ROLE(107002, "存在该权限的角色关联，无法删除"),

    LOGIN_USER_NOT_EXIST(308001, "用户不存在"),
    LOGIN_PASSWORD_ERROR(308002, "密码错误"),
    LOGIN_USER_NOT_LOGIN(408003, "用户未登录"),
    LOGIN_REGISTER_USER_EXIST(208004, "注册用户已存在"),

    LEND_USER_LEND_BOOK_EXIST(309001, "用户已借阅该图书且未归还"),
    LEND_RENEW_OVER_MAX_COUNT(309002, "续借次数超过最大值"),
    LEND_USER_NOT_LEND(209003, "用户未借阅该图书"),
    LEND_BOOK_NOT_EXIST(109004, "借阅书籍不存在"),
    LEND_BOOK_EMPTY(30905, "借阅书籍库存为空"),

    UTIL_PASSWORD_MD5_ERROR(410001, "密码MD5值异常"),

    MODULE_EXIST(111001, "模块已存在");

    private final Integer code;
    private final String msg;
}
