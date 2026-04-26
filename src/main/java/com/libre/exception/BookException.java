package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class BookException extends LibreException {
    public BookException(String msg, Integer code) {
        super(msg, code);
    }

    public BookException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public BookException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public BookException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
