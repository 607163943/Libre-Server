package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class LendException extends LibreException {
    public LendException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
