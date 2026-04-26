package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class UtilException extends CommonException {
    public UtilException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
