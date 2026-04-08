package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class LoginException extends LibreException{
    public LoginException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
