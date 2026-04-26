package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class AuthorizeException extends LibreException{
    public AuthorizeException(String msg, Integer code) {
        super(msg, code);
    }

    public AuthorizeException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public AuthorizeException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public AuthorizeException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
