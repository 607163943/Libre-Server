package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class UserException extends LibreException {
    public UserException(String msg, Integer code) {
        super(msg, code);
    }

    public UserException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public UserException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public UserException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
