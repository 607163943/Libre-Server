package com.libre.exception;

import com.libre.enums.CommonExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private CommonExceptionEnums exceptionEnums;
}
