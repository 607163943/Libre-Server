package com.libre.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LibreException extends RuntimeException {
    private Integer errorCode;
    private String errorMsg;
}
