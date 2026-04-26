package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;

public class PublisherException extends CommonException {
    public PublisherException(CommonExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }
}
