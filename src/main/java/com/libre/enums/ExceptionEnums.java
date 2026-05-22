package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnums {
    AUTHOR_EXIST(10101, "作者已存在"),
    AUTHOR_HAS_BOOK(10102, "存在该作者的图书，无法删除"),

    CATEGORY_EXIST(10301, "分类已存在"),

    BOOK_EXIST(10201, "图书已存在"),
    BOOK_HAS_LEND(10202, "图书存在借阅记录，无法删除"),
    BOOK_NUMBER_OVER_ERROR(10203, "可借图书数量超过库存"),
    BOOK_NUMBER_ERROR(10204, "图书数量库存错误，可用库存低于0！"),

    NOT_HAS_ROLE(10401, "角色权限不够"),

    LEND_USER_NOT_LEND(10501, "用户未借阅该图书"),
    LEND_USER_LEND_BOOK_EXIST(10502, "用户已借阅该图书且未归还"),
    LEND_BOOK_EMPTY(10503, "借阅书籍库存为空"),
    LEND_RENEW_OVER_MAX_COUNT(10504, "续借次数超过最大值"),
    LEND_BOOK_NOT_EXIST(10505, "借阅书籍不存在"),
    LEND_USER_NOT_EXIST(10506, "借阅用户不存在"),
    LEND_CURRENT_OVERDUE(10507, "当前存在借阅书籍逾期，无法借书"),
    LEND_MAX_COUNT_EXCEED(10509, "已达到最大借书数量限制"),
    LEND_ALREADY_LEND(10511, "该图书已被借阅且未归还"),

    LOGIN_USER_NOT_LOGIN(10601, "用户未登录"),
    LOGIN_USER_NOT_EXIST(10602, "用户不存在"),
    LOGIN_PASSWORD_ERROR(10603, "密码错误"),
    LOGIN_REGISTER_USER_EXIST(10604, "注册用户已存在"),
    LOGIN_USER_DISABLE(10605, "用户已禁用"),
    LOGIN_REGISTER_CAPTCHA_ERROR(10606, "验证码错误"),
    LOGIN_CAPTCHA_ERROR(10607, "验证码认证失败"),
    LOGIN_USER_NOT_ADMIN(10608, "该用户不具备登录权限"),

    PUBLISHER_EXIST(10701, "出版社已存在"),
    PUBLISHER_HAS_BOOK(10702, "存在该出版社出版图书，无法删除"),

    USER_EXIST(10801, "用户已存在"),
    USER_NOT_EXIST(10802, "用户不存在"),
    USER_EMAIL_ALREADY_EXIST(10803, "邮箱已被使用"),
    USER_PHONE_ALREADY_EXIST(10804, "手机号码已被使用"),

    ROLE_EXIST(10901, "角色已存在"),

    USER_ROLE_EXIST(11001, "用户角色关系已存在"),

    UTIL_PASSWORD_MD5_ERROR(11101, "密码MD5值异常"),

    MESSAGE_EXIST(11201, "消息已存在"),
    MESSAGE_NOT_EXIST(11202, "消息不存在"),
    MESSAGE_SEND_ERROR(11203, "消息发送失败"),

    FILE_MD5_ERROR(11301, "文件MD5值异常"),

    USER_PERMISSION_DENIED(11501, "权限不足，无法修改该用户信息");

    private final Integer code;
    private final String msg;
}
