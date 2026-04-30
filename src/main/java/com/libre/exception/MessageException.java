package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class MessageException extends LibreException {
    public MessageException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
