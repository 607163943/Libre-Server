package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class UtilException extends LibreException {
    public UtilException(String msg, Integer code) {
        super(msg, code);
    }

    public UtilException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public UtilException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public UtilException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
