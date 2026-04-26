package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class AuthorException extends LibreException {
    public AuthorException(String msg, Integer code) {
        super(msg, code);
    }

    public AuthorException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public AuthorException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public AuthorException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
