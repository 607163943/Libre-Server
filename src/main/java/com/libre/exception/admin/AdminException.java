package com.libre.exception.admin;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.LibreException;

public class AdminException extends LibreException {
    public AdminException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums.getCode(), exceptionEnums.getMsg());
    }

    public AdminException(AdminExceptionEnums adminExceptionEnums) {
        super(adminExceptionEnums.getCode(), adminExceptionEnums.getMsg());
    }
}
