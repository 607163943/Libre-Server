package com.libre.exception;

import com.libre.enums.AdminExceptionEnums;
import com.libre.enums.AppExceptionEnums;
import com.libre.enums.CommonExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LibreException extends RuntimeException {
    private String msg;
    private Integer code;

    public LibreException(CommonExceptionEnums exceptionEnums) {
        this.code=exceptionEnums.getCode();
        this.msg=exceptionEnums.getMsg();
    }

    public LibreException(AdminExceptionEnums exceptionEnums) {
        this.code=exceptionEnums.getCode();
        this.msg=exceptionEnums.getMsg();
    }

    public LibreException(AppExceptionEnums exceptionEnums) {
        this.code=exceptionEnums.getCode();
        this.msg=exceptionEnums.getMsg();
    }
}
