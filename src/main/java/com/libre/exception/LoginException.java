package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class LoginException extends LibreException {
    public LoginException(String msg, Integer code) {
        super(msg, code);
    }

    public LoginException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public LoginException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public LoginException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
