package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;

public class PublisherException extends LibreException {
    public PublisherException(String msg, Integer code) {
        super(msg, code);
    }

    public PublisherException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public PublisherException(AdminExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public PublisherException(AppExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
