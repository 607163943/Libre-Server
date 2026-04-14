package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnums {
    AUTHOR_EXIST(10001, "作者已存在"),
    AUTHOR_HAS_BOOK(10002, "存在该作者的图书，无法删除"),
    PUBLISHER_EXIST(20001, "出版社已存在"),
    PUBLISHER_HAS_BOOK(20002, "存在该出版社出版图书，无法删除"),
    BOOK_EXIST(30001, "图书已存在"),
    USER_EXIST(40001, "用户已存在"),
    ROLE_EXIST(50001, "角色已存在"),
    USER_ROLE_EXIST(60001, "用户角色关系已存在"),
    USER_NOT_EXIST(70001, "用户不存在"),
    PASSWORD_ERROR(70002, "密码错误"),
    NOT_LOGIN(70003, "用户未登录"),
    LEND_NOT_EXIST(80001, "借阅记录不存在"),
    LEND_ALREADY_RETURNED(80002, "图书已归还"),
    LEND_OVERDUE(80003, "图书已逾期"),
    USER_LEND_BOOK_EXIST(80004, "用户已借阅该图书且未归还"),
    REGISTER_USER_EXIST(90001, "注册用户已存在");
    private final Integer code;
    private final String msg;
}
