package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class UserException extends CommonException {
    public UserException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
