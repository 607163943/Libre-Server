package com.libre.handler;

import com.libre.enums.ExceptionEnums;
import com.libre.exception.AuthorException;
import com.libre.exception.LibreException;
import com.libre.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义作者异常处理
     * @param e 作者异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthorException.class)
    public Result<Void> handleAuthorException(AuthorException e) {
        ExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("作者异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }


    /**
     * 自定义业务异常处理
     * @param e 业务异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LibreException.class)
    public Result<Void> handleLibreException(LibreException e) {
        ExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("未知业务异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 运行时异常处理
     * @param e 运行时异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：{}", e.getMessage());
        return Result.of(500, null, "服务器异常");
    }
}
