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
    PASSWORD_ERROR(70002, "密码错误");
    private final Integer code;
    private final String msg;
}
