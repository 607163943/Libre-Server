package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class PermissionException extends LibreException {
    public PermissionException(String msg, Integer code) {
        super(msg, code);
    }

    public PermissionException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public PermissionException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public PermissionException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
