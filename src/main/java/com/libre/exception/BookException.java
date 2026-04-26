package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class BookException extends LibreException {
    public BookException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
