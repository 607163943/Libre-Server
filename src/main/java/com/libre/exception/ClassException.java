package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class ClassException extends LibreException {
    public ClassException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
