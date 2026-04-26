package com.libre.exception.admin;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class AdminPermissionException extends AdminException {
    public AdminPermissionException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public AdminPermissionException(AdminExceptionEnums adminExceptionEnums) {
        super(adminExceptionEnums);
    }
}
