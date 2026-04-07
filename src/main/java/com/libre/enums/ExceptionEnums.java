package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnums {
    AUTHOR_EXIST(10001, "作者已存在"),
    PUBLISHER_EXIST(20001, "出版社已存在"),
    BOOK_EXIST(30001, "图书已存在");
    private final Integer code;
    private final String msg;
}
