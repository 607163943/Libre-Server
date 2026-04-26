package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class LendException extends LibreException {
    public LendException(String msg, Integer code) {
        super(msg, code);
    }

    public LendException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public LendException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public LendException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
