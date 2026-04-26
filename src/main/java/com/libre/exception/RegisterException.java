package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class RegisterException extends LibreException {
    public RegisterException(String msg, Integer code) {
        super(msg, code);
    }

    public RegisterException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public RegisterException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public RegisterException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
