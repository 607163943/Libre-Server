package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class PermissionException extends LibreException {
    public PermissionException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
