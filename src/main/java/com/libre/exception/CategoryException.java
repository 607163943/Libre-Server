package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class CategoryException extends LibreException {
    public CategoryException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
