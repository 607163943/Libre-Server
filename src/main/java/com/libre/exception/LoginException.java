package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class LoginException extends CommonException {
    public LoginException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
