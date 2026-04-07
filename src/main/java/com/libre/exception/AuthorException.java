package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class AuthorException extends LibreException{
    public AuthorException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
