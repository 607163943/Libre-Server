package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class PermissionException extends CommonException {
    public PermissionException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
