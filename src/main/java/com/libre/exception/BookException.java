package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class BookException extends CommonException {
    public BookException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
