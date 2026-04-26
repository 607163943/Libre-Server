package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class ModuleException extends CommonException {
    public ModuleException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
