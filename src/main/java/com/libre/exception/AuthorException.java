package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class AuthorException extends CommonException {
    public AuthorException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
