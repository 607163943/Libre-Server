package com.libre.handler;

import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.*;
import com.libre.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义登录模块异常处理
     *
     * @param e 登录模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginException.class)
    public Result<Void> handleLoginException(LoginException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("登录模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 自定义注册模块异常处理
     *
     * @param e 注册模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegisterException.class)
    public Result<Void> handleRegisterException(RegisterException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("注册模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 自定义出版社模块异常处理
     *
     * @param e 出版社模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PublisherException.class)
    public Result<Void> handlePublisherException(PublisherException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("出版社模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 自定义角色模块异常处理
     *
     * @param e 角色模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoleException.class)
    public Result<Void> handleRoleException(RoleException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("角色模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 自定义用户模块异常处理
     *
     * @param e 用户模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public Result<Void> handleUserException(UserException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("用户模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 自定义图书模块异常处理
     *
     * @param e 图书模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BookException.class)
    public Result<Void> handleBookException(BookException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("图书模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 自定义作者模块异常处理
     *
     * @param e 作者模块异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthorException.class)
    public Result<Void> handleAuthorException(AuthorException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("作者模块异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }


    /**
     * 自定义业务异常处理
     *
     * @param e 业务异常
     * @return 错误信息
     */
    // TODO:异常分离后需要修改
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommonException.class)
    public Result<Void> handleLibreException(CommonException e) {
        CommonExceptionEnums exceptionEnums = e.getExceptionEnums();
        log.warn("未知业务异常：{}", exceptionEnums.getMsg());
        return Result.of(exceptionEnums.getCode(), null, exceptionEnums.getMsg());
    }

    /**
     * 运行时异常处理
     *
     * @param e 运行时异常
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        // 获取原始请求路径（防止被 /error 覆盖）
        Object originalPath = request.getAttribute("javax.servlet.error.request_uri");
        String realPath = (originalPath != null) ? originalPath.toString() : request.getRequestURI();

        log.error("捕获异常路径: {} (原始路径: {}) 异常信息:{}", request.getRequestURI(), realPath, e.getMessage());
        return Result.of(500, null, "服务器异常");
    }
}
