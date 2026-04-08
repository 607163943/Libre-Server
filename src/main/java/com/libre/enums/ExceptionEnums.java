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
    USER_EXIST(40001, "用户已存在");
    private final Integer code;
    private final String msg;
}
