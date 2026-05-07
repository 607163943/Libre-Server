package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class UploadException extends LibreException{
    public UploadException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
