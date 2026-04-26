package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class RoleException extends LibreException {
    public RoleException(String msg, Integer code) {
        super(msg, code);
    }

    public RoleException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public RoleException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public RoleException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
