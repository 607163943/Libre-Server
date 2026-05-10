package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class LendReviewException extends LibreException{
    public LendReviewException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
