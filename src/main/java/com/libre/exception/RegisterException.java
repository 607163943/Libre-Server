package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class RegisterException extends LibreException{
    public RegisterException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
