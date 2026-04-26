package com.libre.exception.app;

import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.LibreException;

public class AppException extends LibreException {
    public AppException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums.getCode(), exceptionEnums.getMsg());
    }

    public AppException(AppExceptionEnums appExceptionEnums) {
        super(appExceptionEnums.getCode(), appExceptionEnums.getMsg());
    }
}
