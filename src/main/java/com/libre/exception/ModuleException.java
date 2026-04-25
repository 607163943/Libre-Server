package com.libre.exception;

import com.libre.enums.ExceptionEnums;

public class ModuleException extends LibreException{
    public ModuleException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
