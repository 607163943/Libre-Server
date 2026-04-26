package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class UtilException extends LibreException {
    public UtilException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
