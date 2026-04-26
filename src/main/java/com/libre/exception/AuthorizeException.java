package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class AuthorizeException extends LibreException{
    public AuthorizeException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
