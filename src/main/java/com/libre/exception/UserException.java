package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class UserException extends LibreException {
    public UserException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
