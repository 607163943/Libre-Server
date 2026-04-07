package com.libre.exception;

import com.libre.enums.ExceptionEnums;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BookException extends LibreException {
    public BookException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
