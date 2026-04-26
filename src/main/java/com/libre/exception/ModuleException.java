package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class ModuleException extends LibreException {
    public ModuleException(String msg, Integer code) {
        super(msg, code);
    }

    public ModuleException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public ModuleException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public ModuleException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
