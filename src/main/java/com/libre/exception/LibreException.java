package com.libre.exception;

import com.libre.enums.ExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LibreException extends RuntimeException {
    private ExceptionEnums exceptionEnums;
}
